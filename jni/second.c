#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <jni.h>
#include "android/log.h"

#define LOG_TAG "MyTag"
#define LOGV(...)   __android_log_print(ANDROID_LOG_VERBOSE, LOG_TAG, __VA_ARGS__)

extern int first();
#define MAX_BUTTON 9

jint JNICALL Java_com_example_androidex_MainActivity_add(JNIEnv *env, jobject this)
{
	LOGV("log test %d", 1234);
	unsigned char push_sw_buff[MAX_BUTTON];

	int dev = open("/dev/fpga_push_switch", O_RDWR);
	LOGV("dev : %d\n", dev);
	if (dev<0) {
		printf("Device Open Error\n");
		close(dev);
		return -1;
	}

	int buff_size = sizeof(push_sw_buff);

	read(dev, &push_sw_buff, buff_size);

	int i = 0;
	int ret =0;
	for(i = 0; i < 9; i++){
		if(push_sw_buff[i] == 1){
			push_sw_buff[i] = 0;
			LOGV("butt : %d\n", i);
			ret = i;
			break;
		}
	}
	close(dev);
	return ret;
}

void JNICALL Java_com_example_androidex_MainActivity_testString(JNIEnv *env, jobject this, jstring string)
{
	const char *str=(*env)->GetStringUTFChars( env, string, 0);
	jint len = (*env)->GetStringUTFLength( env, string );
	LOGV("native testString len %d", len);
	LOGV("native testString %s", str);

	(*env)->ReleaseStringUTFChars( env, string, str );	
}
