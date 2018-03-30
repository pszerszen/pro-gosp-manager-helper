#include "StoreGenerator.hpp"
#include "../data/Store.hpp"
#include "../fileWritter/universalPrinter.hpp"

static std::string addresses[/**/] = 
{
	"Piekielna 1 58-506 Wroclaw",
	"Piekielna 1 58-506 Wroclaw",
	"Piekielna 1 58-506 Wroclaw",
	"Piekielna 1 58-506 Wroclaw",
	"Piekielna 1 58-506 Wroclaw",
	"Piekielna 1 58-506 Wroclaw",
	"Piekielna 1 58-506 Wroclaw",
	"Piekielna 1 58-506 Wroclaw",
	"Piekielna 1 58-506 Wroclaw",
	"Piekielna 1 58-506 Wroclaw",
	"Piekielna 1 58-506 Wroclaw",
	"Piekielna 1 58-506 Wroclaw",
	"Piekielna 1 58-506 Wroclaw",
	"Piekielna 1 58-506 Wroclaw",
	"Piekielna 1 58-506 Wroclaw",
	"Piekielna 1 58-506 Wroclaw",
	"Piekielna 1 58-506 Wroclaw",
};

static std::string namesForShops[/**/] = 
{
	"A",
	"B",
	"C",
	"D",
	"E",
	"F",
	"G",
	"H",
	"I",
	"J",
	"K",
	"L",
};


int main()
{

	StoreGenerator sg;
	universalPrinter<Store> up;

	Store st1 = sg.generateStore(addresses[0],namesForShops[0]);
	Store st2 = sg.generateStore(addresses[2],namesForShops[3]);
	up.changeObj(st1);
	up.printOnCout();
	std::cout << std::endl;
	up.changeObj(st2);
	up.printOnCout();

	return 0;
}