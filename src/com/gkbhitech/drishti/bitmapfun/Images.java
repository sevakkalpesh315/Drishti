/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gkbhitech.drishti.bitmapfun;

import java.util.ArrayList;

import com.gkbhitech.drishti.bitmapfun.ImageWorker.ImageWorkerAdapter;


/**
 * Some simple test data to use for this sample app.
 */
public class Images {

	public final static ArrayList<Integer> ResourceList = new ArrayList<Integer>();

	/**
	 * This are PicasaWeb URLs and could potentially change. Ideally the
	 * PicasaWeb API should be used to fetch the URLs.
	 */

	public final static String[] imageUrls = new String[] {
			"http://i50.tinypic.com/1e3mgh.png",
			"http://i47.tinypic.com/2e2e6w8.png"

	};

	/**
	 * This are PicasaWeb thumbnail URLs and could potentially change. Ideally
	 * the PicasaWeb API should be used to fetch the URLs.
	 */
	public final static String[] imageThumbUrls = new String[] {
			"http://i50.tinypic.com/1e3mgh.png",
			"http://i47.tinypic.com/2e2e6w8.png"
			
	};

	/**
	 * Simple static adapter to use for images.
	 */
	public final static ImageWorkerAdapter imageWorkerUrlsAdapter = new ImageWorkerAdapter() {
		@Override
		public Object getItem(int num) {
			return Images.imageUrls[num];
		}

		@Override
		public int getSize() {
			return Images.imageUrls.length;
		}
	};

	/**
	 * Simple static adapter to use for image thumbnails.
	 */
	public final static ImageWorkerAdapter imageThumbWorkerUrlsAdapter = new ImageWorkerAdapter() {
		@Override
		public Object getItem(int num) {
			return Images.imageThumbUrls[num];
		}

		@Override
		public int getSize() {
			return Images.imageThumbUrls.length;
		}
	};

	/**
	 * Simple static adapter to use for image thumbnails.
	 */
	public final static ImageWorkerAdapter imageResourceWorkerAdapter = new ImageWorkerAdapter() {

		@Override
		public Object getItem(int num) {
			return Images.ResourceList.get(num);
		}

		@Override
		public int getSize() {
			return Images.ResourceList.size();
		}
	};

}
