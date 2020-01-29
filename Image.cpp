#include"Image.h"
#include "array2d.hpp"
#include"vec3.h"
#include"imageio.h"
#include "ppm.h"
#include<algorithm>
bool Image::load(const std::string& filename, const std::string& format) {
	float* rgbArr = image::ReadPPM(filename.c_str(), (int*)&width, (int*)&height);
	if (!rgbArr) return false;
	for (unsigned int i = 0; i < width * height; i++) {
		buffer.push_back(math::Vec3<float>(rgbArr[3 * i], rgbArr[3 * i +1], rgbArr[3 * i +2]));
	}
	delete[] rgbArr;
	return true;
}
bool Image::save(const std::string& filename, const std::string& format) {
	float* rgbArr = new float[3 * width * height];
	int index = 0;
	for (size_t i = 0; i < width * height; i++) {
		rgbArr[3*i] = buffer[i].r;
		rgbArr[3*i +1] = buffer[i].g;
		rgbArr[3*i +2] = buffer[i].b;
	}
	std::string newfilename = "filtered_" + filename;
	bool done = image::WritePPM(rgbArr, width, height, newfilename.c_str());
	delete[] rgbArr;
	return done;
}
Image& Image::operator=( Image& img)
{
	width = img.width;
	height = img.height;
	buffer = img.buffer;
	return *this;
}

Image::Image(unsigned int w, unsigned int h, const color* data_ptr ): Array2D(w,h,data_ptr){}

Image::Image(const Image& src):Array2D(src){}

Image::~Image() {
	buffer.clear();
	buffer.shrink_to_fit();
}