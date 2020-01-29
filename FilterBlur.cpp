#include "FilterBlur.h"
#include <cmath>
#include "filter.h"
#include "array2d.h"
#include "Image.h"

FilterBlur::FilterBlur(int N) : N(N) {
	buffer.resize(N);
	for (int i = 0; i < N * N; ++i) {
		buffer[i] = 1 / (float)N * N;
	}
}

Image FilterBlur::operator<<(const Image& image) {
	Image img = image;
	int area = n / 2;
	for (size_t i = 0; i < img.getHeight(); i++) { //iterating through the image
		for (size_t j = 0; j < img.getWidth(); j++) {
			color sum(0, 0, 0);
			for (int m = -area; m <= area; m++) {
				for (int c = -area; c <= area; c++) {
					if (0 <= i + m && i + m < img.getHeight() &&
						0 <= j + c && j + c < img.getWidth()) {
						sum += img(j + c, i + m) * this->operator()(area + m, area + c);
					}
				}
			}
			sum.clampToUpperBound(1.0);
			sum.clampToLowerBound(0.0);
			img(j, i) = sum;
		}
	}
	return img;
}

