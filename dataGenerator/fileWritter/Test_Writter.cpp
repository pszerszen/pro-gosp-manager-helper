//Test_Writter.hpp

#include "IWritter.hpp"
#include "../data/Store.hpp"

int main()
{
	Store store = 	
	{	
		1,
		"Gorska 5",
		"WIELKA WYPRZ",
		789456123,
		Stationary,
	};

	std::ofstream myfile;
  	myfile.open ("example.txt");
	universalWritter<Store> writter;
	writter.changeObj(&store);
	writter.writeToFile(myfile);
	myfile.close();
	return 0;
}


