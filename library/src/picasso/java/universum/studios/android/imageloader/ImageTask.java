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
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

import java.io.IOException;

/**
 * A {@link BaseImageTask} implementation that can be used to load remote image with <b>Url</b> target.
 *
 * @author Martin Albedinsky
 */
public final class ImageTask extends BaseImageTask<Picasso, String, Transformation> {

	/**
	 * Interface ===================================================================================
	 */

	/**
	 * Constants ===================================================================================
	 */

	/**
	 * Log TAG.
	 */
	private static final String TAG = "ImageTask";

	/**
	 * Static members ==============================================================================
	 */

	/**
	 * Target implementation used for asynchronous loading without image view provided.
	 */
	private static final Target ASYNC_TARGET = new Target() {

		/**
		 */
		@Override
		public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
			// Ignored.
		}

		/**
		 */
		@Override
		public void onBitmapFailed(Drawable errorDrawable) {
			// Ignored.
		}

		/**
		 */
		@Override
		public void onPrepareLoad(Drawable placeHolderDrawable) {
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
	protected boolean onLoad(@NonNull Picasso loader, @Nullable final ImageLoader.Callback callback) {
		ensureHasTargetOrThrow();
		final RequestCreator requestCreator = onPrepareRequestCreator(loader);
		if (mView != null) requestCreator.into(mView, callback != null ? new Listener(this, callback) : null);
		else requestCreator.into(new Target() {

			/**
			 */
			@Override
			public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
				if (callback != null) callback.onImageLoadFinished(ImageTask.this, bitmap);
			}

			/**
			 */
			@Override
			public void onBitmapFailed(Drawable errorDrawable) {
				if (callback != null) callback.onImageLoadFailed(
						ImageTask.this,
						new ImageLoader.Error(
								ImageLoader.Error.REASON_UNKNOWN,
								"Failed to load image."
						)
				);
			}

			/**
			 */
			@Override
			public void onPrepareLoad(Drawable placeHolderDrawable) {
				// Ignored.
			}
		});
		return true;
	}

	/**
	 */
	@Override
	protected Bitmap onLoad(@NonNull Picasso loader) {
		ensureHasTargetOrThrow();
		final RequestCreator requestCreator = onPrepareRequestCreator(loader);
		try {
			return requestCreator.get();
		} catch (IOException e) {
			throw new ImageLoader.Error(
					ImageLoader.Error.REASON_IO,
					"Failed to load image.",
					e
			);
		}
	}

	/**
	 * Prepares loading request creator with configuration based on this task's parameters.
	 *
	 * @param loader Loader used to obtain the creator.
	 * @return Prepared image loading request creator to be executed.
	 */
	@NonNull
	protected RequestCreator onPrepareRequestCreator(@NonNull Picasso loader) {
		final RequestCreator creator = loader.load(mTarget);
		if (mPlaceholderRes != NO_RESOURCE_ID) creator.error(mPlaceholderRes);
		if (mPlaceholder != null) creator.error(mPlaceholder);
		if (mErrorRes != NO_RESOURCE_ID) creator.placeholder(mErrorRes);
		if (mError != null) creator.placeholder(mError);
		if (mTransformation != null) creator.transform(mTransformation);
		if (hasRequest(REQUEST_DO_NOT_ANIMATE)) creator.noFade();
		return creator;
	}

	/**
	 */
	@Override
	protected boolean onRemove(@NonNull Picasso loader) {
		throw new UnsupportedOperationException("Picasso image loader does not support removing of loaded images.");
	}

	/**
	 * Inner classes ===============================================================================
	 */

	/**
	 * Listener that wraps implementation of {@link Callback} for a specific {@link ImageLoader.Task}
	 * and {@link ImageLoader.Callback}.
	 */
	private static final class Listener implements Callback {

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
		@SuppressWarnings("ConstantConditions")
		public void onSuccess() {
			final Drawable image = task.view().getDrawable();
			final Bitmap bitmap = image instanceof BitmapDrawable ? ((BitmapDrawable) image).getBitmap() : null;
			if (bitmap == null) {
				Log.w(TAG, "Failed to retrieve loaded bitmap from ImageView. Image drawable(" + image + ") is not instance of BitmapDrawable.");
			}
			callback.onImageLoadFinished(task, bitmap);
		}

		/**
		 */
		@Override
		public void onError() {
			callback.onImageLoadFailed(
					task,
					new ImageLoader.Error(
							ImageLoader.Error.REASON_UNKNOWN,
							"Failed to load image."
					)
			);
		}
	}
}
