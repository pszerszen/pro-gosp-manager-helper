#include "SupplyGenerator.hpp"
#include "../data/Supply.hpp"
#include "../fileWritter/universalPrinter.hpp"


int main()
{

	SupplyGenerator sg;
	universalPrinter<Supply> up;

	Supply st1 = sg.generateSupply("12.03.2015",1,1);
	Supply st2 = sg.generateSupply("12.03.2015",2,3);
	up.changeObj(st1);
	up.printOnCout();
	std::cout << std::endl;
	up.changeObj(st2);
	up.printOnCout();

	return 0;
}