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

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

import java.util.concurrent.ExecutionException;

/**
 * A {@link BaseImageTask} implementation that can be used to load remote image with <b>Url</b> target.
 *
 * @author Martin Albedinsky
 */
public class ImageTask extends BaseImageTask<RequestManager, String, BitmapTransformation> {

	/**
	 * Constants ===================================================================================
	 */

	/**
	 * Log TAG.
	 */
	// private static final String TAG = "ImageTask";

	/**
	 * Interface ===================================================================================
	 */

	/**
	 * Static members ==============================================================================
	 */

	/**
	 * Target implementation used for asynchronous loading without image view provided.
	 */
	private static final Target<GlideDrawable> ASYNC_TARGET = new SimpleTarget<GlideDrawable>() {

		/**
		 */
		@Override
		public void onResourceReady(GlideDrawable resource, GlideAnimation glideAnimation) {
			// Ignored.
		}
	};

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
	protected boolean onLoad(@NonNull RequestManager loader, @Nullable ImageLoader.Callback callback) {
		ensureHasTargetOrThrow();
		final DrawableTypeRequest<String> request = onPrepareRequest(loader);
		if (callback != null) request.listener(new Listener(this, callback));
		if (mView != null) request.into(mView);
		else request.into(ASYNC_TARGET);
		return true;
	}

	/**
	 */
	@Nullable
	@Override
	protected Bitmap onLoad(@NonNull RequestManager loader) {
		ensureHasTargetOrThrow();
		final DrawableTypeRequest<String> request = onPrepareRequest(loader);
		try {
			return request.asBitmap().into(-1, -1).get();
		} catch (InterruptedException | ExecutionException e) {
			throw new ImageLoader.Error(
					ImageLoader.Error.REASON_UNKNOWN,
					"Failed to load image.",
					e
			);
		}
	}

	/**
	 * Prepares loading request with configuration based on this task's parameters.
	 *
	 * @param requestManager Manager used to create the request.
	 * @return Prepared image loading request to be executed.
	 */
	@NonNull
	protected DrawableTypeRequest<String> onPrepareRequest(@NonNull RequestManager requestManager) {
		final DrawableTypeRequest<String> request = requestManager.load(mTarget);
		if (mPlaceholderRes != NO_RESOURCE_ID) request.error(mPlaceholderRes);
		if (mPlaceholder != null) request.error(mPlaceholder);
		if (mErrorRes != NO_RESOURCE_ID) request.placeholder(mErrorRes);
		if (mError != null) request.placeholder(mError);
		if (mTransformation != null) request.transform(mTransformation);
		if (hasRequest(REQUEST_DO_NOT_ANIMATE)) request.dontAnimate();
		return request;
	}

	/**
	 */
	@Override
	protected boolean onRemove(@NonNull RequestManager loader) {
		throw new UnsupportedOperationException("Glide image loader does not support removing of loaded images.");
	}

	/**
	 * Inner classes ===============================================================================
	 */

	/**
	 * Listener that wraps implementation of {@link RequestListener} for a specific {@link ImageLoader.Task}
	 * and {@link ImageLoader.Callback}.
	 */
	private static final class Listener implements RequestListener<String, GlideDrawable> {

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
		public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
			callback.onImageLoadFinished(task, ((GlideBitmapDrawable) resource).getBitmap());
			return false;
		}

		/**
		 */
		@Override
		public boolean onException(Exception exception, String model, Target<GlideDrawable> target, boolean isFirstResource) {
			callback.onImageLoadFailed(task, errorFromException(exception));
			return false;
		}

		/**
		 * Creates a new instance of Error from the given <var>exception</var>.
		 *
		 * @param exception The exception from which to create the error.
		 * @return New error instance.
		 */
		private static ImageLoader.Error errorFromException(Exception exception) {
			return new ImageLoader.Error(
					ImageLoader.Error.REASON_UNKNOWN,
					"Failed to load image.",
					exception
			);
		}
	}
}
