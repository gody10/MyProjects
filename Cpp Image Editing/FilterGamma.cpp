#include "FilterGamma.h"
#include "Image.h"
#include<cmath>
#include "vec3.h"

 FilterGamma::FilterGamma(float _gamma){
	 if (_gamma < 0.5f) gamma = 0.5f;
	 else if (_gamma > 2.0f) gamma = 2.f;
	 else gamma = _gamma;
 }

 FilterGamma::FilterGamma(const FilterGamma & filter){
	 gamma = filter.gamma;
 }

 Image FilterGamma::operator<<(const Image& img) {
	 Image _img(img);
	 printf("Applying gamma filter...\n");
	 for (unsigned int j = 0; j < _img.getHeight(); j++) {
		 for (unsigned int i = 0; i < _img.getWidth(); i++) {
			 float r = std::pow(_img(i, j).r, gamma);
			 float b = std::pow(_img(i, j).g, gamma);
			 float g = std::pow(_img(i, j).b, gamma);
			 color pixel(r, g, b);
			 pixel.clampToUpperBound(1.f);
			 pixel.clampToLowerBound(0.f);
			 _img(i, j) = pixel;
		 }
	 }
	 return _img;
}


	 //filteredRGB.clampToUpperBound(1.f);
	 //filteredRGB.clampToLowerBound(0.f);