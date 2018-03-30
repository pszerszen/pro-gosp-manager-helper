#ifndef STORE_HPP
#define STORE_HPP

#include <string>
#include <boost/fusion/adapted/struct.hpp>
#include <boost/fusion/include/for_each.hpp>
#include <boost/phoenix/phoenix.hpp>

enum StoreType
{
	Stationary = 1,
	Internet = 2
};

struct Store
{
	unsigned shopId;
	std::string address;
	std::string name;
	unsigned phoneNumber;
	StoreType type;
};

BOOST_FUSION_ADAPT_STRUCT(Store, (unsigned,shopId)(std::string,address)(std::string,name)(unsigned,phoneNumber)(StoreType,type))
#endif // STORE_HPP