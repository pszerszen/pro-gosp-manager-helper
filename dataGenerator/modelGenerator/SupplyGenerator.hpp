#ifndef SUPPLYGENERATOR_HPP
#define SUPPLYGENERATOR_HPP

#include <stdlib.h>     /* srand, rand */
#include <time.h>       /* time */
#include "../data/Supply.hpp"


class SupplyGenerator
{
public:
	SupplyGenerator():supplayCnt(0)
	{
		srand (time(NULL));
	}

	Supply generateSupply(const std::string date,const unsigned productId,const unsigned storeId)
	{
		Supply supp;
		supp.supplyId = supplayCnt;
		supplayCnt++;
		supp.date = date;
		supp.type = SupplyType::delivered;
		supp.quantity = rand() % 10 + 2;
		supp.productId = productId;
		supp.receiverId = storeId;
		return supp;
	}

private:
	unsigned supplayCnt;

};

#endif