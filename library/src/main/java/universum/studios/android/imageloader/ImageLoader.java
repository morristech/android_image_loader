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

import android.app.Activity;
import android.app.Fragment;
import android.content.ComponentCallbacks2;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

/**
 * Unified image loader interface.
 *
 * @author Martin Albedinsky
 */
public interface ImageLoader {

	/**
	 * Interface ===================================================================================
	 */

	/**
	 * Listener callback that can be used to listen whether the loading process for a specific
	 * {@link Task} has been finished successfully or has failed.
	 *
	 * @author Martin Albedinsky
	 * @see #load(Task, Callback)
	 */
	interface Callback {

		/**
		 * Invoked whenever the image loading process is successfully finished for the specified
		 * <var>task</var>.
		 *
		 * @param task   The task for which loading process has finished successfully.
		 * @param bitmap Image bitmap loaded for the task.
		 */
		void onImageLoadFinished(@NonNull Task task, @NonNull Bitmap bitmap);

		/**
		 * Invoked whenever the image loading process failed due to specified <var>error</var>.
		 *
		 * @param task  The task for which loading process has finished with error.
		 * @param error The error due to which has the task failed.
		 * @see ImageLoader.Error#reason
		 * @see ImageLoader.Error#getCause()
		 */
		void onImageLoadFailed(@NonNull Task task, @NonNull Error error);
	}

	/**
	 * Task used to specify set of desired parameters necessary for image loading process.
	 *
	 * @param <Target>         Type of the target that is used by Task implementation to load requested image.
	 * @param <Transformation> Type of the transformation that can be applied to the loaded image.
	 * @author Martin Albedinsky
	 * @see #load(Task)
	 * @see #load(Task, Callback)
	 * @see #remove(Task)
	 */
	interface Task<Target, Transformation> {

		/**
		 * Constant determining that no resource id has been specified.
		 */
		int NO_RESOURCE_ID = 0;

		/**
		 * Specifies a target that refers to the image bitmap stored whether <b>online</b> on a remote
		 * server or <b>offline</b> within this Android device's cache (memory, disk) to be loaded via
		 * this task.
		 *
		 * @param target The desired target.
		 * @return This task to allow methods chaining.
		 * @see #target()
		 * @see #placeholder(Drawable)
		 * @see #error(Drawable)
		 */
		Task<Target, Transformation> target(@NonNull Target target);

		/**
		 * Returns the target referring to the image bitmap to be loaded.
		 *
		 * @return The image bitmap target.
		 * @see #target(Object)
		 */
		@NonNull
		Target target();

		/**
		 * Same as {@link #placeholder(Drawable)} for resource id.
		 *
		 * @param resId Resource id of the desired placeholder drawable. May be {@link #NO_RESOURCE_ID}
		 *              to not use any.
		 * @return This task to allow methods chaining.
		 */
		Task<Target, Transformation> placeholder(@DrawableRes int resId);

		/**
		 * Specifies a drawable to be set to the associated image view while image loading process
		 * is finished.
		 *
		 * @param placeholder The desired placeholder drawable. May be {@code null} to not use any.
		 * @return This task to allow methods chaining.
		 * @see #error(Drawable)
		 */
		Task<Target, Transformation> placeholder(@Nullable Drawable placeholder);

		/**
		 * Same as {@link #error(Drawable)} for resource id.
		 *
		 * @param resId Resource id of the desired error drawable. May be {@link #NO_RESOURCE_ID}
		 *              to not use any.
		 * @return This task to allow methods chaining.
		 */
		Task<Target, Transformation> error(@DrawableRes int resId);

		/**
		 * Specifies a drawable to be set to the associated image view when image loading process
		 * fails due to some error.
		 *
		 * @param error The desired error drawable. May be {@code null} to not use any.
		 * @return This task to allow methods chaining.
		 * @see #placeholder(Drawable)
		 */
		Task<Target, Transformation> error(@Nullable Drawable error);

		/**
		 * Specifies a transformation to be applied to the loaded bitmap before it is set to the
		 * associated image view.
		 *
		 * @param transformation The desired transformation. May be {@code null} to not use any.
		 * @return This task to allow methods chaining.
		 */
		Task<Target, Transformation> transform(@Nullable Transformation transformation);

		/**
		 * Specifies a boolean flag determining whether to animate attaching of image bitmap to the
		 * associated image view or not.
		 *
		 * @param animate {@code True} to animate bitmap attaching, {@code false} otherwise.
		 * @return This task to allow methods chaining.
		 */
		Task<Target, Transformation> doNotAnimate(boolean animate);

		/**
		 * Specifies an image view to which should be attached image bitmap loaded via this task.
		 *
		 * @param view The desired image view.
		 * @return This task to allow methods chaining.
		 * @see #view()
		 * @see #transform(Object)
		 */
		Task<Target, Transformation> view(@NonNull ImageView view);

		/**
		 * Returns the image view specified for this task.
		 *
		 * @return Image view to which will be attached image bitmap if loaded successfully.
		 * @see #view(ImageView)
		 */
		@Nullable
		ImageView view();
	}

	/**
	 * Error exception that will be dispatched by {@link ImageLoader} if some error occurs during
	 * loading process.
	 *
	 * @author Martin Albedinsky
	 * @see #load(ImageLoader.Task)
	 * @see ImageLoader.Callback#onImageLoadFailed(ImageLoader.Task, ImageLoader.Error)
	 */
	final class Error extends RuntimeException {

		/**
		 * Reason indicating that an {@link Error} has occurred due to an <b>unknown</b> reason.
		 */
		public static final int REASON_UNKNOWN = 0x00;

		/**
		 * Reason indicating that an {@link Error} has occurred due to an <b>IO</b> error.
		 */
		public static final int REASON_IO = 0x01;

		/**
		 * Reason indicating that an {@link Error} has occurred due to a <b>decoding</b> error.
		 */
		public static final int REASON_DECODING = 0x02;

		/**
		 * Reason indicating that an {@link Error} has occurred due to a <b>network</b> error.
		 */
		public static final int REASON_NETWORK = 0x03;

		/**
		 * Reason indicating that an {@link Error} has occurred due to a <b>decoding</b> error.
		 */
		public static final int REASON_MEMORY = 0x04;

		/**
		 * Reason due to which has been this error dispatched.
		 */
		public final int reason;

		/**
		 * Same as {@link #Error(int, String, Throwable)} with {@code null} <var>throwable</var>.
		 */
		public Error(int reason, @NonNull String detailMessage) {
			this(reason, detailMessage, null);
		}

		/**
		 * Creates a new instance of Error with the specified parameters.
		 *
		 * @param reason        The reason of the error.
		 * @param detailMessage The desired detail message for the error.
		 * @param cause         An exception that caused the error.
		 */
		public Error(int reason, @NonNull String detailMessage, @Nullable Throwable cause) {
			super(detailMessage, cause);
			this.reason = reason;
		}
	}

	/**
	 * Methods =====================================================================================
	 */

	/**
	 * Starts this image loader implementation.
	 */
	void start();

	/**
	 * Resumes this image loader implementation.
	 * <p>
	 * Depending on implementation, this should resume all previously paused image tasks.
	 *
	 * @see #pause()
	 */
	void resume();

	/**
	 * Pauses this image loader implementation.
	 * <p>
	 * Depending on implementation, this should pause all currently running image tasks in a way where
	 * they may be resumed via call to {@link #resume()}.
	 *
	 * @see #resume()
	 */
	void pause();

	/**
	 * Stops this image loader implementation.
	 * <p>
	 * Depending on implementation, this should stop all currently running image tasks.
	 *
	 * @see #start()
	 */
	void stop();

	/**
	 * Destroys this image loader implementation.
	 * <p>
	 * Depending on implementation, this should stop all currently running image tasks.
	 */
	void destroy();

	/**
	 * Performs <b>asynchronous</b> loading of a desired image bitmap using the specified <var>task</var>.
	 *
	 * @param task     The task that specifies necessary parameters for the loading process.
	 * @param callback Callback to be invoked whenever the loading process is finished successfully
	 *                 or due to some error.
	 * @return {@code True} if loading process has been initiated, {@code false} if some error has
	 * occurred.
	 */
	boolean load(@NonNull Task task, @Nullable Callback callback);

	/**
	 * Performs <b>synchronous</b> loading of a desired image bitmap using the specified <var>task</var>.
	 *
	 * @param task The task that specifies necessary parameters for the loading process.
	 * @return Loaded image bitmap or {@code null} if loading process has failed.
	 * @throws ImageLoader.Error If some loader related error occurs during the loading process.
	 */
	@Nullable
	Bitmap load(@NonNull Task task);

	/**
	 * Performs <b>synchronous</b> removing of a desired image bitmap using the specified <var>task</var>.
	 *
	 * @param task The task that specifies necessary parameters for the removing process.
	 * @return {@code True} if image bitmap has been successfully removed from the cache (either
	 * memory, disk or both), {@code false} if there was no bitmap to remove.
	 */
	boolean remove(@NonNull Task task);

	/**
	 * Called due to call to {@link Activity#onLowMemory()} or {@link Fragment#onLowMemory()} of the
	 * activity or fragment that uses this image loader.
	 * <p>
	 * Depending on implementation, this image loader should trim the memory used to store loaded
	 * bitmaps the same way as for call to {@link #onTrimMemory(int)} with {@link ComponentCallbacks2#TRIM_MEMORY_COMPLETE TRIM_MEMORY_COMPLETE}.
	 */
	void onLowMemory();

	/**
	 * Called due to call to {@link Activity#onTrimMemory(int)} or {@link Fragment#onTrimMemory(int)}
	 * of the activity or fragment that uses this image loader.
	 * <p>
	 * Depending on implementation, this image loader should trim the memory used to store loaded
	 * bitmaps according to the requested <var>level</var>.
	 *
	 * @param level The level for which should this image loader implementation trim the memory.
	 */
	void onTrimMemory(int level);
}
