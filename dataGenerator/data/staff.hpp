#ifndef Staff_HPP
#define Staff_HPP

#include <string>
#include <boost/fusion/adapted/struct.hpp>
#include <boost/fusion/include/for_each.hpp>
#include <boost/phoenix/phoenix.hpp>



struct Staff
{
	unsigned Id;
	std::bool active;
	std::double bonus;
	std::string date_from;
	std::string date_until;
	std::string first_name;
	std::string last_name
	unsigned salary;
	unsigned id_store;

};

BOOST_FUSION_ADAPT_STRUCT(Staff, (unsigned,Id)(std::bool,active)(std::double,bonus)(std::string,date_from)(std::string,date_until)
(std::string,first_name)(std::string,last_name)(unsigned,salary)(unsigned,id_store))
#endif // Staff_HPP
