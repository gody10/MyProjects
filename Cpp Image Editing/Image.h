#ifndef _IMAGE
#define _IMAGE
#include "array2d.hpp"
#include"vec3.h"
#include"imageio.h"
#include "ppm.h"
#include "filter.h"

typedef math::Vec3<float> color;
class Image : public virtual math::Array2D<color>, public image::ImageIO {
	public:
		Image(unsigned int width = 0, unsigned int height = 0, const color* data_ptr = nullptr);
		Image(const Image& image);
		~Image();
		bool load(const std::string& filename, const std::string& format);
		bool save(const std::string& filename, const std::string& format);
		Image& operator=( Image& img);
};

#endif