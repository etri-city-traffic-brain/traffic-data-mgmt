/*
 * Copyright 2008-2009 MOPAS(Ministry of Public Administration and Security).
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
package net.etri.rest.api.trace;

/**
 * Sample Source : DefaultTraceHandler 
 *
 */
public class DefaultTraceHandler implements TraceHandler {
	/**
	 todo 메소드 
 	 @param clazz 클래스정보
 	 @param message 보여주고자하는 메세지
	*/
	public void todo(@SuppressWarnings("rawtypes") Class clazz, String message) {

		System.out.println(" log ==> DefaultTraceHandler run...............");
	}

}