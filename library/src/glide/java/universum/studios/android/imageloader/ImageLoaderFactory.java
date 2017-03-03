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
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.bumptech.glide.Glide;

/**
 * Factory providing instances of {@link ImageLoader} implementations.
 *
 * @author Martin Albedinsky
 */
public abstract class ImageLoaderFactory {

	/**
	 * Constants ===================================================================================
	 */

	/**
	 * Log TAG.
	 */
	// private static final String TAG = "ImageLoaderFactory";

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
	 * Creates a new empty instance of ImageLoaderFactory. Inheritance hierarchies should declare
	 * theirs constructors private in order to became a standard utility classes.
	 */
	protected ImageLoaderFactory() {
		// We allow to override this class only so it may be used as base for image loader factory.
	}

	/**
	 * Methods =====================================================================================
	 */

	/**
	 * Creates a new instance of {@link ImageLoader} implementation for the given activity <var>context</var>.
	 *
	 * @param context The context for which to create the loader.
	 * @return New image loader implementation ready to be used.
	 * @see #createLoader(FragmentActivity)
	 * @see #createLoader(Fragment)
	 * @see #createLoader(android.support.v4.app.Fragment)
	 * @see #createLoader(Context)
	 */
	@NonNull
	public static ImageLoader createLoader(@NonNull Activity context) {
		return new ImageLoaderImpl(Glide.with(context));
	}

	/**
	 * Creates a new instance of {@link ImageLoader} implementation for the given fragment activity
	 * <var>context</var>.
	 *
	 * @param context The context for which to create the loader.
	 * @return New image loader implementation ready to be used.
	 * @see #createLoader(Activity)
	 * @see #createLoader(Fragment)
	 * @see #createLoader(android.support.v4.app.Fragment)
	 * @see #createLoader(Context)
	 */
	@NonNull
	public static ImageLoader createLoader(@NonNull FragmentActivity context) {
		return new ImageLoaderImpl(Glide.with(context));
	}

	/**
	 * Creates a new instance of {@link ImageLoader} implementation for the given fragment <var>context</var>.
	 *
	 * @param context The context for which to create the loader.
	 * @return New image loader implementation ready to be used.
	 * @see #createLoader(Activity)
	 * @see #createLoader(FragmentActivity)
	 * @see #createLoader(android.support.v4.app.Fragment)
	 * @see #createLoader(Context)
	 */
	@NonNull
	public static ImageLoader createLoader(@NonNull Fragment context) {
		return new ImageLoaderImpl(Glide.with(context));
	}

	/**
	 * Creates a new instance of {@link ImageLoader} implementation for the given support fragment
	 * <var>context</var>.
	 *
	 * @param context The context for which to create the loader.
	 * @return New image loader implementation ready to be used.
	 * @see #createLoader(Activity)
	 * @see #createLoader(FragmentActivity)
	 * @see #createLoader(Fragment)
	 * @see #createLoader(Context)
	 */
	@NonNull
	public static ImageLoader createLoader(@NonNull android.support.v4.app.Fragment context) {
		return new ImageLoaderImpl(Glide.with(context));
	}

	/**
	 * Creates a new instance of {@link ImageLoader} implementation for the given <var>context</var>.
	 *
	 * @param context The context for which to create the loader.
	 * @return New image loader implementation ready to be used.
	 * @see #createLoader(Activity)
	 * @see #createLoader(FragmentActivity)
	 * @see #createLoader(Fragment)
	 * @see #createLoader(android.support.v4.app.Fragment)
	 */
	@NonNull
	public static ImageLoader createLoader(@NonNull Context context) {
		return new ImageLoaderImpl(Glide.with(context));
	}

	/**
	 * Inner classes ===============================================================================
	 */
}
