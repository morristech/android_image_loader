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
import android.util.Log;

import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * A {@link BaseImageLoader} implementation that wraps instance of {@link com.nostra13.universalimageloader.core.ImageLoader}
 * loader used to perform loading for {@link ImageTask ImageTasks}.
 *
 * @author Martin Albedinsky
 */
final class ImageLoaderImpl extends BaseImageLoader<com.nostra13.universalimageloader.core.ImageLoader> {

	/**
	 * Interface ===================================================================================
	 */

	/**
	 * Constants ===================================================================================
	 */

	/**
	 * Log TAG.
	 */
	private static final String TAG = "ImageLoader#Universal";

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
	protected ImageLoaderImpl(@NonNull ImageLoader loader) {
		super(loader);
	}

	/**
	 * Methods =====================================================================================
	 */

	/**
	 */
	@Override
	public void start() {
		resume();
	}

	/**
	 */
	@Override
	public void resume() {
		if (mLoader.isInited()) mLoader.resume();
	}

	/**
	 */
	@Override
	public void pause() {
		if (mLoader.isInited()) mLoader.pause();
	}

	/**
	 */
	@Override
	public void stop() {
		if (mLoader.isInited()) mLoader.stop();
	}

	/**
	 */
	@Override
	public void destroy() {
		if (mLoader.isInited()) mLoader.destroy();
	}

	/**
	 */
	@Override
	public void onLowMemory() {
		Log.w(TAG, "Running low on memory.");
	}

	/**
	 */
	@Override
	public void onTrimMemory(int level) {
		Log.w(TAG, "Should trim memory for level(" + level + ").");
	}

	/**
	 * Inner classes ===============================================================================
	 */
}
