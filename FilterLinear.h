#ifndef _FILTERL
#define _FILTERL
#include "vec3.h"
#include "filter.h"
#include "Image.h"
typedef math::Vec3<float> color;
class FilterLinear : public Filter {
	friend class Image;
private:
	color a;
	color c;
public:
	FilterLinear(double ax, double bx, double cx, double ay, double by, double cy);
	FilterLinear(color _a = 0 , color _c = 0);
	FilterLinear(const FilterLinear& filter);
	Image operator << (const Image& img);
};

#endif