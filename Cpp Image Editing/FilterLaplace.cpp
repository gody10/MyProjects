#include "FilterLaplace.h"
typedef unsigned short shrt;
FilterLaplace::FilterLaplace() {
	buffer = { 0,1,0,1, - 4,1,0,1,0 };
}

Image FilterLaplace::operator<< (const Image& image) {
	Image img(image);
	unsigned int w = img.getWidth();
	unsigned int h = img.getHeight();
	for (size_t j = 0; j < h; j++){
		for (size_t i = 0; i < w; i++)	{
			color sum(0, 0, 0);
			for (int n = -1; n <= 1; n++) {
				for (int m = -1; m <= 1; m++) {
					if (j + n < img.getWidth() && j + n >= 0 && i + m >= 0 && i + m < img.getHeight()) //If the pixel is within range
						sum += img(j + n, i + m) * this->operator()(n + 1, m + 1);
				}
			}
			sum.clampToUpperBound(1.0f);
			sum.clampToLowerBound(0.0f);
			img(i, j) = sum;
			}
		}
	return img;
	}