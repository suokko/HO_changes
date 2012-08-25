#include "ho_core_util_Win32ShellWrapper.h"
#include <jni.h>
#ifdef WIN32
#include <shlobj.h>
#else
#include <stdlib.h>
#include <string.h>
#define MAX_PATH 200
#endif

JNIEXPORT jstring JNICALL Java_ho_core_util_Win32ShellWrapper_GetAppDataPath(JNIEnv *env, jobject obj)
{
	(void)obj;
	char path[MAX_PATH];
#ifdef WIN32
	HRESULT rv = SHGetFolderPath(NULL,
			CSIDL_APPDATA,
			NULL,
			0,
			path);

	if (!SUCCEEDED(rv))
		return NULL;
#else
	strcpy(path, getenv("HOME"));
	strcpy(path + strlen(path), "/.config");
#endif
	jbyteArray bArray = NULL;
	jstring r = NULL;
	jclass classString = (*env)->FindClass(env, "java/lang/String");
	int len = strlen(path);

	if (!classString)
		goto fail;

	bArray = (*env)->NewByteArray(env, len);
	if (!bArray)
		goto fail;

	jmethodID mid = (*env)->GetMethodID(env, classString, "<init>", "([B)V");
	if (!mid)
		goto fail;

	(*env)->SetByteArrayRegion(env, bArray, 0, len, (jbyte *)path);
	(*env)->GetMethodID(env, classString, "<init>", "([B)V");
	r = (*env)->NewObject(env, classString, mid, bArray);
fail:
	if (bArray)
		(*env)->DeleteLocalRef(env, bArray);
	if (classString)
		(*env)->DeleteLocalRef(env, classString);
	return r;
}
