#ifndef TRANSACTIONGENERATOR_HPP
#define TRANSACTIONGENERATOR_HPP

#include <stdlib.h>     /* srand, rand */
#include <time.h>       /* time */
#include "../data/Transaction.hpp"


class TransactionGenerator
{
public:
	TransactionGenerator():transactionCnt(0)
	{
		srand (time(NULL));
	}

	Transaction generateDummy(std::string date,unsigned productId, unsigned storeId, unsigned staffId )
	{	
		Transaction transaction;
		transaction.transactionId = transactionCnt;
		transactionCnt++;
		transaction.date = date;
		transaction.quantity = rand() % 5 ;
		transaction.productId = productId;
		transaction.staffId = staffId;
		transaction.storeId = storeId;

		return transaction;
	}

private:

	unsigned transactionCnt;
};

#endif