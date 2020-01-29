#ifndef FILTER_BLUR_H
#define FILTER_BLUR_H
#include "filter.h"
class FilterBlur : public Filter, public math::Array2D<float> {
protected:
	int N;
	int m = 0;
	int n=0;
public:
	FilterBlur(int N = 0);
	Image operator<< (const Image& image);
};
#endif // !1



