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

import android.content.Context;
import android.support.annotation.NonNull;

import com.squareup.picasso.Picasso;

/**
 * Factory providing instances of {@link ImageLoader} implementations.
 *
 * @author Martin Albedinsky
 */
public class ImageLoaderFactory {

	/**
	 * Interface ===================================================================================
	 */

	/**
	 * Constants ===================================================================================
	 */

	/**
	 * Log TAG.
	 */
	// private static final String TAG = "ImageLoaderFactory";

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
	 * Creates a new instance of {@link ImageLoader} implementation for the given <var>context</var>.
	 *
	 * @param context The context for which to create the loader.
	 * @return New image loader implementation ready to be used.
	 */
	@NonNull
	public static ImageLoader createLoader(@NonNull Context context) {
		return new ImageLoaderImpl(Picasso.with(context));
	}

	/**
	 * Methods =====================================================================================
	 */

	/**
	 * Inner classes ===============================================================================
	 */
}
