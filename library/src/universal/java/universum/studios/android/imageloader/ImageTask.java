/*
 * =================================================================================================
 *                             Copyright (C) 2016 Universum Studios
 * =================================================================================================
 *         Licensed under the Apache License, Version 2.0 or later (further "License" only).
 * -------------------------------------------------------------------------------------------------
 * You may use this file only in compliance with the License. More details and copy of this License
 * you may obtain at
 *
 * 		http://www.apache.org/licenses/LICENSE-2.0
 *
 * You can redistribute, modify or publish any part of the code written within this file but as it
 * is described in the License, the software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES or CONDITIONS OF ANY KIND.
 *
 * See the License for the specific language governing permissions and limitations under the License.
 * =================================================================================================
 */
package universum.studios.android.imageloader;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.nostra13.universalimageloader.cache.disc.DiskCache;
import com.nostra13.universalimageloader.cache.memory.MemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * A {@link BaseImageTask} implementation that can be used to load remote image with <b>Url</b> target.
 *
 * @author Martin Albedinsky
 */
public class ImageTask extends BaseImageTask<com.nostra13.universalimageloader.core.ImageLoader, String, BitmapDisplayer> {

	/**
	 * Interface ===================================================================================
	 */

	/**
	 * Constants ===================================================================================
	 */

	/**
	 * Log TAG.
	 */
	// private static final String TAG = "ImageTask";

	/**
	 * Static members ==============================================================================
	 */

	/**
	 * Members =====================================================================================
	 */

	/**
	 * Constructors ================================================================================
	 */

	/**
	 * Methods =====================================================================================
	 */

	/**
	 */
	@Override
	protected boolean onLoad(@NonNull com.nostra13.universalimageloader.core.ImageLoader loader, @Nullable ImageLoader.Callback callback) {
		ensureHasTargetOrThrow();
		final DisplayImageOptions displayOptions = onPrepareDisplayOptionsBuilder(new DisplayImageOptions.Builder()).build();
		final Listener listener = callback == null ? null : new Listener(this, callback);
		if (mView == null) {
			loader.loadImage(mTarget, displayOptions, listener);
		} else {
			loader.displayImage(mTarget, mView, displayOptions, listener);
		}
		return true;
	}

	/**
	 */
	@NonNull
	@Override
	protected Bitmap onLoad(@NonNull com.nostra13.universalimageloader.core.ImageLoader loader) {
		ensureHasTargetOrThrow();
		final DisplayImageOptions displayOptions = onPrepareDisplayOptionsBuilder(new DisplayImageOptions.Builder()).build();
		return loader.loadImageSync(mTarget, displayOptions);
	}

	/**
	 * Prepares builder for display options with configuration based on this task's parameters.
	 *
	 * @param builder Instance of options builder to prepare.
	 * @return Prepared builder ready to build image display options to be used during loading process.
	 */
	@NonNull
	protected DisplayImageOptions.Builder onPrepareDisplayOptionsBuilder(@NonNull DisplayImageOptions.Builder builder) {
		if (mPlaceholderRes != NO_RESOURCE_ID) builder.showImageOnLoading(mPlaceholderRes);
		if (mPlaceholder != null) builder.showImageOnLoading(mPlaceholder);
		if (mErrorRes != NO_RESOURCE_ID) builder.showImageOnFail(mErrorRes);
		if (mError != null) builder.showImageOnFail(mError);
		if (mTransformation != null) {
			if (!(mTransformation instanceof FadeInBitmapDisplayer) || !hasRequest(REQUEST_DO_NOT_ANIMATE)) {
				builder.displayer(mTransformation);
			}
		}
		return builder;
	}

	/**
	 */
	@Override
	protected boolean onRemove(@NonNull com.nostra13.universalimageloader.core.ImageLoader loader) {
		ensureHasTargetOrThrow();
		final MemoryCache memoryCache = loader.getMemoryCache();
		final boolean removedFromMemory = memoryCache != null && memoryCache.remove(mTarget) != null;
		final DiskCache diskCache = loader.getDiskCache();
		final boolean removedFromDisk = diskCache != null && diskCache.remove(mTarget);
		return removedFromMemory || removedFromDisk;
	}

	/**
	 * Inner classes ===============================================================================
	 */

	/**
	 * Listener that wraps implementation of {@link ImageLoadingListener} for a specific {@link ImageLoader.Task}
	 * and {@link ImageLoader.Callback}.
	 */
	private static final class Listener implements ImageLoadingListener {

		/**
		 * Task for which has been loading performed.
		 */
		final ImageLoader.Task task;

		/**
		 * Loader callback to be invoked in case of finished loading or failed loading.
		 */
		final ImageLoader.Callback callback;

		/**
		 * Creates a new Listener wrapper for the given <var>task</var> and <var>callback</var>.
		 *
		 * @param task     The task for which has been loading performed.
		 * @param callback The loader callback to be invoked in case of finished loading or failed loading.
		 */
		Listener(ImageLoader.Task task, ImageLoader.Callback callback) {
			this.task = task;
			this.callback = callback;
		}

		/**
		 */
		@Override
		public void onLoadingStarted(String uri, View view) {
			// Ignored.
		}

		/**
		 */
		@Override
		public void onLoadingCancelled(String uri, View view) {
			// Ignored.
		}

		/**
		 */
		@Override
		public void onLoadingComplete(String uri, View view, Bitmap bitmap) {
			callback.onImageLoadFinished(task, bitmap);
		}

		/**
		 */
		@Override
		public void onLoadingFailed(String uri, View view, FailReason failReason) {
			callback.onImageLoadFailed(task, errorFromFailReason(failReason));
		}

		/**
		 * Creates a new instance of Error from the given <var>failReason</var>.
		 *
		 * @param failReason The reason from which to create the error.
		 * @return New error instance.
		 */
		private static ImageLoader.Error errorFromFailReason(FailReason failReason) {
			final int reason;
			switch (failReason.getType()) {
				case IO_ERROR:
					reason = ImageLoader.Error.REASON_IO;
					break;
				case DECODING_ERROR:
					reason = ImageLoader.Error.REASON_DECODING;
					break;
				case NETWORK_DENIED:
					reason = ImageLoader.Error.REASON_NETWORK;
					break;
				case OUT_OF_MEMORY:
					reason = ImageLoader.Error.REASON_MEMORY;
					break;
				case UNKNOWN:
				default:
					reason = ImageLoader.Error.REASON_UNKNOWN;
					break;
			}
			return new ImageLoader.Error(reason, "Failed to load image.", failReason.getCause());
		}
	}
}
