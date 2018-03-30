#ifndef SUPPLY_HPP
#define SUPPLY_HPP


#include <string>
#include <boost/fusion/adapted/struct.hpp>
#include <boost/fusion/include/for_each.hpp>
#include <boost/phoenix/phoenix.hpp>

enum SupplyType
{
	ordered = 0,
	inProgress ,
	delivered
};


struct Supply
{
	unsigned supplyId;
	std::string date;
	SupplyType type;
	int quantity;
	unsigned productId;
	unsigned receiverId;
};


BOOST_FUSION_ADAPT_STRUCT(Supply, (unsigned,supplyId)(std::string,date)(SupplyType,type)(int, quantity)(unsigned,productId)(unsigned,receiverId))
#endif