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

/**
 * Base implementation of {@link ImageLoader} that accepts implementations of{@link BaseImageTask}
 * via {@link #load(Task)}, {@link #load(Task, Callback)} and {@link #remove(Task)} methods.
 * <p>
 * This class simply wraps instance of a specific image loader and can be used as base for a custom
 * implementation of {@link ImageLoader} interface.
 *
 * @param <L> Type of the loader that will be used by {@link BaseImageTask} to perform loading or
 *            removing of a specific image.
 * @author Martin Albedinsky
 */
public abstract class BaseImageLoader<L> implements ImageLoader {

	/**
	 * Constants ===================================================================================
	 */

	/**
	 * Log TAG.
	 */
	// private static final String TAG = "BaseImageLoader";

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
	 * Wrapped loader used to perform image loading or removing.
	 */
	protected final L mLoader;

	/**
	 * Constructors ================================================================================
	 */

	/**
	 * Creates a new instance of BaseImageLoader wrapper for the given <var>loader</var> instance.
	 *
	 * @param loader The loader to be wrapped and used to perform image loading or removing process.
	 */
	protected BaseImageLoader(@NonNull L loader) {
		this.mLoader = loader;
	}

	/**
	 * Methods =====================================================================================
	 */

	/**
	 */
	@Override
	@SuppressWarnings("unchecked")
	public boolean load(@NonNull Task task, @Nullable Callback callback) {
		if (task instanceof BaseImageTask) {
			((BaseImageTask) task).onLoad(mLoader, callback);
			return true;
		}
		return false;
	}

	/**
	 */
	@Nullable
	@Override
	@SuppressWarnings("unchecked")
	public Bitmap load(@NonNull Task task) {
		return task instanceof BaseImageTask ? ((BaseImageTask) task).onLoad(mLoader) : null;
	}

	/**
	 */
	@Override
	@SuppressWarnings("unchecked")
	public boolean remove(@NonNull Task task) {
		return task instanceof BaseImageTask && ((BaseImageTask) task).onRemove(mLoader);
	}

	/**
	 * Inner classes ===============================================================================
	 */
}
