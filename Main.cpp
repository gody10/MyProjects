#include "array2d.h"
#include "FilterLinear.h"
#include "FilterGamma.h"
#include "Image.h"
#include"ppm.h"
#include "vec3.h"
#include <iostream>
#include <string>
#include<vector>
#include <iostream>
#include"FilterBlur.h"
#include "FilterLaplace.h"
typedef unsigned short ushort;

int main(int argc, char* argv[]) {
	size_t index;
	Image mainImage;
	std::string filename = argv[argc - 1];
	ushort filterCounter = 0;
	bool saveImg = false;
	bool loadImg = mainImage.load(filename, "ppm");
	if (loadImg) {
		std::cout << filename << " loaded successfully" << std::endl;
	}
	else {
		std::cerr << "No image found, EXTERMINATE" << std::endl;
		exit(EXIT_FAILURE);
	}
	for (size_t i = 1; i < argc - 1; i++) {
		index = i;
		if (std::string(argv[index]) == "-f") {
			index++;
			if (std::string(argv[index]) == "gamma") {
				float exp;
				filterCounter++;
				ushort paramCounter = 0;
				if (isdigit(*argv[++index])) {
					exp = atof(argv[index]);
					paramCounter++;
				}
				if (paramCounter == 1) {
					FilterGamma filter(exp);
					Image img = filter << mainImage;
					mainImage = img;
				}
				else {
					std::cerr << "Missing parameter, Gamma filter won't be applied!" << std::endl;
				}
			}
			else if (std::string(argv[index]) == "linear") {
				filterCounter++;
				ushort paramCounter = 0;
				float aR, aG, aB, cR, cG, cB;
				if (isdigit(*argv[++index])) {
					aR = std::stof(argv[index]);
					++paramCounter;
				}
				if (isdigit(*argv[++index])) {
					aG = std::stof(argv[index]);
					++paramCounter;
				}
				if (isdigit(*argv[++index])) {
					aB = std::stof(argv[index]);
					++paramCounter;
				}
				if (isdigit(*argv[++index])) {
					cR = std::stof(argv[index]);
					++paramCounter;
				}
				if (isdigit(*argv[++index])) {
					cG = std::stof(argv[index]);
					++paramCounter;
				}
				if (isdigit(*argv[++index])) {
					cB = std::stof(argv[index]);
					++paramCounter;
				}
				if (paramCounter == 6) {
					FilterLinear filter(color(aR, aG, aB), color(cR, cG, cB));
					Image img = filter << mainImage;
					mainImage = img;
				}
				else {
					std::cerr << "Filter values are missing. Linear filter won't be applied!" << std::endl;
				}
			}
			else if(std::string(argv[index]) == "laplace"){
				FilterLaplace filter;
				Image img = filter << mainImage;
				mainImage = img;
			}
			else if (std::string(argv[index]) == "blur") {
				float param;
				int paramCounter=0;
				if (isdigit(*argv[++index])) {
					param = std::stof(argv[index]);
					paramCounter++;
				}
				if (paramCounter == 1) {
					FilterGamma filter(param);
					Image img = filter << mainImage;
					mainImage = img;
				}
				else {
					std::cerr << "Missing parameter, Gamma filter won't be applied!" << std::endl;
				}
			}
		}
	}
	if (!filterCounter) {
		std::cout << "No filter were given, EXTERMINATE" << std::endl;
		exit(EXIT_FAILURE);
	}
	saveImg = mainImage.save(filename, "ppm");
	if (saveImg) {
		std::cout << "File saved successfully" << std::endl;
	}
	else {
		exit(EXIT_FAILURE);
	}
	return(EXIT_SUCCESS);
}



