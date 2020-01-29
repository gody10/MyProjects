#include "FilterLinear.h"
#include"Image.h"
#include "vec3.h"

FilterLinear::FilterLinear(double ax, double bx, double cx, double ay, double by, double cy):a(ax,bx,cx), c(ay,by,cy){
}

FilterLinear::FilterLinear(color _a , color _c): a(_a), c(_c){}

FilterLinear::FilterLinear(const FilterLinear& filter): a(filter.a), c(filter.c){}

Image FilterLinear::operator<<(const Image& img) {
	Image _img(img);
	printf("Applying linear filter...\n");
	for (unsigned int j = 0; j < _img.getHeight(); j++) {
		for (unsigned int i = 0; i < _img.getWidth(); i++) {
			color pix = a * _img(i, j);
			pix += c;
			pix.clampToLowerBound(0.f);
			pix.clampToUpperBound(1.f);
			_img(i, j) = pix;
		}
	}
	return _img;
}