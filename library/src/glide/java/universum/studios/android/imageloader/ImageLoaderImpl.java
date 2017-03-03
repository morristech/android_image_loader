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

import android.support.annotation.NonNull;

import com.bumptech.glide.RequestManager;

/**
 * A {@link BaseImageLoader} implementation that wraps instance of {@link RequestManager} loader used
 * to perform loading for {@link ImageTask ImageTasks}.
 *
 * @author Martin Albedinsky
 */
final class ImageLoaderImpl extends BaseImageLoader<RequestManager> {

	/**
	 * Constants ===================================================================================
	 */

	/**
	 * Log TAG.
	 */
	// private static final String TAG = "ImageLoader#Glide";

	/**
	 * Interface ===================================================================================
	 */

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
	 * Creates a new instance of ImageLoaderImpl to wrap the given <var>loader</var>.
	 *
	 * @param loader The loader to be used to perform images loading.
	 */
	protected ImageLoaderImpl(@NonNull RequestManager loader) {
		super(loader);
	}

	/**
	 * Methods =====================================================================================
	 */

	/**
	 */
	@Override
	public void start() {
		mLoader.onStart();
	}

	/**
	 */
	@Override
	public void resume() {
		start();
	}

	/**
	 */
	@Override
	public void pause() {
		stop();
	}

	/**
	 */
	@Override
	public void stop() {
		mLoader.onStop();
	}

	/**
	 */
	@Override
	public void destroy() {
		mLoader.onDestroy();
	}

	/**
	 */
	@Override
	public void onTrimMemory(int level) {
		mLoader.onTrimMemory(level);
	}

	/**
	 */
	@Override
	public void onLowMemory() {
		mLoader.onLowMemory();
	}

	/**
	 * Inner classes ===============================================================================
	 */
}
