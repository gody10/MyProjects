#include "ppm.h"
#include <fstream>
#include <string>
#include <iomanip>
#include <iostream>
#include <algorithm>

float * image::ReadPPM(const char* filename, int* w, int* h){
	std::ifstream file(filename, std::ios::in | std::ios::binary);
	if (!file.is_open()) {
		printf("Could not open PPM file");
		return nullptr;
	}
	std::string title;
	int maxVal;
	file >> title >> *w >> *h >> maxVal;
	if (title != "P6") {
		std::cout << "not supported format" << std::endl;
		return nullptr;
	}
	long length = 3* *h * *w;
	unsigned char* buffer = new unsigned char[length];
	float* arr = new float[length];
	file.read((char*)buffer, length);
	for (int i = 0; i < length; i++) {
		arr[i] = buffer[i] / 255.0f;
	}
	delete[] buffer;
	return arr;
}	



bool image::WritePPM(const float* data, int w, int h, const char* filename) {
	std::ofstream file(filename, std::ios::out | std::ios::binary);
	if (!file.is_open()) {
		printf("Could not open PPM file");
		return false;
	}
	file << "P6\n" << w << '\n'<< h<<'\n' << "255"<<'\t'<<'\t'<<'\t';
	for (int i = 0; i < 3 * w *h; i++) {
		unsigned char str = static_cast<unsigned char>(std::min(1.f, data[i]) * 255);
		file << str;
	}
	return true;
}