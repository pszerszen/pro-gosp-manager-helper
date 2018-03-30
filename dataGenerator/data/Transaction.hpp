#ifndef TRANSACTION_HPP
#define TRANSACTION_HPP
#include <string>
#include <boost/fusion/adapted/struct.hpp>
#include <boost/fusion/include/for_each.hpp>
#include <boost/phoenix/phoenix.hpp>

struct Transaction
{
	unsigned transactionId;
	std::string date;
	unsigned quantity;
	unsigned productId;
	unsigned staffId;
	unsigned storeId;
};

BOOST_FUSION_ADAPT_STRUCT(Transaction, (unsigned,transactionId)(std::string,date)(unsigned,quantity)(unsigned,productId)(unsigned,staffId)(unsigned,storeId))
#endif TRANSACTION_HPP