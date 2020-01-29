#ifndef FILTER_LAPLACE_H
#define FILTER_LAPLACE_H


#include "FilterBlur.h"

class FilterLaplace : public FilterBlur{
	public:
		FilterLaplace();
		Image operator << (const Image& image);
	};


#endif // !FILTER_LAPLACE_H
