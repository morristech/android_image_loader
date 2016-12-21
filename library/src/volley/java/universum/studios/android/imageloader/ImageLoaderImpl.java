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

/**
 * @author Martin Albedinsky
 */
final class ImageLoaderImpl extends BaseImageLoader<Void> {

	/**
	 * Interface ===================================================================================
	 */

	/**
	 * Constants ===================================================================================
	 */

	/**
	 * Log TAG.
	 */
	private static final String TAG = "ImageLoader#Volley";

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
	 * @param loader
	 */
	protected ImageLoaderImpl(@NonNull Void loader) {
		super(loader);
	}

	/**
	 * Methods =====================================================================================
	 */

	/**
	 */
	@Override
	public void start() {
		// Ignored.
	}

	/**
	 */
	@Override
	public void resume() {
		// Ignored.
	}

	/**
	 */
	@Override
	public void pause() {
		// Ignored.
	}

	/**
	 */
	@Override
	public void stop() {
		// Ignored.
	}

	/**
	 */
	@Override
	public void destroy() {
		// Ignored.
	}

	/**
	 */
	@Override
	public void onTrimMemory(int level) {
		Log.w(TAG, "Should trim memory for level(" + level + ").");
	}

	/**
	 */
	@Override
	public void onLowMemory() {
		Log.w(TAG, "Running low on memory.");
	}

	/**
	 * Inner classes ===============================================================================
	 */
}
