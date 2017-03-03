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
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Base implementation of {@link ImageLoader.Task} that implements setters for all task parameters.
 * <p>
 * This class can be used as base for a custom implementation of {@link ImageLoader.Task} interface
 * to be used in conjunction with {@link BaseImageLoader}.
 *
 * @param <Loader>         Type of the specific loader that will be used by subclass of BaseImageTask
 *                         to perform image loading or removing process.
 * @param <Target>         Type of the target that is used by Task implementation to load requested
 *                         image.
 * @param <Transformation> Type of the transformation that can be applied to the loaded image.
 * @author Martin Albedinsky
 * @see BaseImageLoader
 */
public abstract class BaseImageTask<Loader, Target, Transformation> implements ImageLoader.Task<Target, Transformation> {

	/**
	 * Constants ===================================================================================
	 */

	/**
	 * Log TAG.
	 */
	// private static final String TAG = "BaseImageTask";

	/**
	 * Interface ===================================================================================
	 */

	/**
	 * Static members ==============================================================================
	 */

	/**
	 * Request flag indicating that an attachment process of a loaded image to its associated view
	 * should not be animated.
	 * <p>
	 * <b>Request method:</b> {@link #doNotAnimate(boolean)}
	 *
	 * @see #hasRequest(int)
	 */
	protected static final int REQUEST_DO_NOT_ANIMATE = 0x00000001;

	/**
	 *
	 */
	@IntDef({
			REQUEST_DO_NOT_ANIMATE
	})
	@Retention(RetentionPolicy.SOURCE)
	protected @interface Request {
	}

	/**
	 * Members =====================================================================================
	 */

	/**
	 * Target which to load.
	 */
	protected Target mTarget;

	/**
	 * Drawable to be set to {@link #mView} during loading process.
	 */
	protected Drawable mPlaceholder;

	/**
	 * Resource id of a drawable to be set to {@link #mView} during loading process.
	 */
	protected int mPlaceholderRes = NO_RESOURCE_ID;

	/**
	 * Drawable to be set to {@link #mView} when some error occurs.
	 */
	protected Drawable mError;

	/**
	 * Resource id of a drawable to be set to {@link #mView} when some error occurs.
	 */
	protected int mErrorRes = NO_RESOURCE_ID;

	/**
	 * Transformation to be applied to the loaded bitmap before it is set to {@link #mView}.
	 */
	protected Transformation mTransformation;

	/**
	 * Set of requests for this task.
	 */
	private int mRequests;

	/**
	 * ImageView for which to perform loading.
	 */
	protected ImageView mView;

	/**
	 * Constructors ================================================================================
	 */

	/**
	 * Methods =====================================================================================
	 */

	/**
	 */
	@Override
	public ImageLoader.Task<Target, Transformation> target(@NonNull Target target) {
		this.mTarget = target;
		return this;
	}

	/**
	 */
	@NonNull
	@Override
	public Target target() {
		return mTarget;
	}

	/**
	 */
	@Override
	public ImageLoader.Task<Target, Transformation> placeholder(@DrawableRes int resId) {
		this.mPlaceholderRes = resId;
		this.mPlaceholder = null;
		return this;
	}

	/**
	 */
	@Override
	public ImageLoader.Task<Target, Transformation> placeholder(@Nullable Drawable placeholder) {
		this.mPlaceholder = placeholder;
		this.mPlaceholderRes = NO_RESOURCE_ID;
		return this;
	}

	/**
	 */
	@Override
	public ImageLoader.Task<Target, Transformation> error(@DrawableRes int resId) {
		this.mErrorRes = resId;
		this.mError = null;
		return this;
	}

	/**
	 */
	@Override
	public ImageLoader.Task<Target, Transformation> error(@Nullable Drawable error) {
		this.mError = error;
		this.mErrorRes = NO_RESOURCE_ID;
		return this;
	}

	/**
	 */
	@Override
	public ImageLoader.Task<Target, Transformation> transform(@Nullable Transformation transformation) {
		this.mTransformation = transformation;
		return this;
	}

	/**
	 */
	@Override
	public ImageLoader.Task<Target, Transformation> doNotAnimate(boolean animate) {
		this.updateRequests(REQUEST_DO_NOT_ANIMATE, animate);
		return this;
	}

	/**
	 */
	@Override
	public ImageLoader.Task<Target, Transformation> view(@NonNull ImageView view) {
		this.mView = view;
		return this;
	}

	/**
	 */
	@NonNull
	@Override
	public ImageView view() {
		return mView;
	}

	/**
	 * Updates the current request flags.
	 *
	 * @param request  Request flag to register/unregister into/from the current request flags.
	 * @param register {@code True} to add the specified request flag into the current ones,
	 *                 {@code false} to remove it.
	 */
	private void updateRequests(int request, boolean register) {
		if (register) this.mRequests |= request;
		else this.mRequests &= ~request;
	}

	/**
	 * Checks whether the specified <var>request</var> flag is registered for this task or not.
	 *
	 * @param request The desired request flag to check. One of {@link #REQUEST_DO_NOT_ANIMATE}.
	 * @return {@code True} if request has been registered, {@code false} otherwise.
	 */
	protected final boolean hasRequest(@Request int request) {
		return (mRequests & request) != 0;
	}

	/**
	 * Called to perform image loading process <b>asynchronously</b> via the given <var>loader</var>
	 * based on the parameters specified for this task.
	 *
	 * @param loader   The loader to be used to perform image loading.
	 * @param callback Callback to be invoked when the loading process is finished successfully or
	 *                 finished due to some failure. May be {@code null}.
	 * @return {@code True} if loading process has been initiated, {@code false} if some error has
	 * occurred.
	 */
	protected abstract boolean onLoad(@NonNull Loader loader, @Nullable ImageLoader.Callback callback);

	/**
	 * Called to perform image loading process <b>synchronously</b> via the given <var>loader</var>
	 * based on the parameters specified for this task.
	 *
	 * @param loader The loader to be used to perform image loading.
	 * @return Loaded image bitmap or {@code null} if loading process has failed.
	 * @throws ImageLoader.Error If some loader related error occurs during the loading process.
	 */
	@Nullable
	protected abstract Bitmap onLoad(@NonNull Loader loader);

	/**
	 * Called to perform image removing process via the given <var>loader</var> based on the parameters
	 * specified for this task.
	 *
	 * @param loader The loader to be used to perform image removing.
	 * @return {@code True} if image bitmap has been successfully removed from the cache (either
	 * memory, disk or both), {@code false} if there was no bitmap to remove.
	 */
	protected abstract boolean onRemove(@NonNull Loader loader);

	/**
	 * Ensures that this task has its <var>target</var> parameter specified.
	 * If not an {@link IllegalArgumentException} is thrown.
	 */
	protected final void ensureHasTargetOrThrow() {
		if (mTarget == null) throw new IllegalArgumentException("No target specified");
	}

	/**
	 * Ensures that this task has its <var>view</var> parameter specified.
	 * If not an {@link IllegalArgumentException} is thrown.
	 */
	protected final void ensureHasViewOrThrow() {
		if (mView == null) throw new IllegalArgumentException("No view specified.");
	}

	/**
	 * Inner classes ===============================================================================
	 */
}
