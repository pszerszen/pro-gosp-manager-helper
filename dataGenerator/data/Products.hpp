#ifndef Products_HPP
#define Products_HPP

#include <string>
#include <boost/fusion/adapted/struct.hpp>
#include <boost/fusion/include/for_each.hpp>
#include <boost/phoenix/phoenix.hpp>



struct Products
{
	unsigned Id;
	std::string model;
	std::string name;
	std::string producer;
	std::double purchase_price;
	std::double sales_price;

};

BOOST_FUSION_ADAPT_STRUCT(Products, (unsigned,Id)(std::string,model)
(std::string,name)(std::string,producer)(std::double,purchase_price)
(std::double,sales_price))
#endif // Products_HPP
