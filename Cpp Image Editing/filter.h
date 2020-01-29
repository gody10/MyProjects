#pragma once
#include"Image.h"

class Filter
{
friend class Image;
public:
	Image virtual  operator << (const Image& img) = 0;
};