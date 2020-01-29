#ifndef _FILTERG
#define _FILTERG
#include "vec3.h"
#include "filter.h"
#include "Image.h"
class FilterGamma : public Filter {
	friend class Image;
private:
	float gamma;
public:
	FilterGamma(float _gamma = 0.f);
	FilterGamma(const FilterGamma& filter);
	Image operator << (const Image& img);
};

#endif