#ifndef Users_HPP
#define Users_HPP


#include <string>
#include <boost/fusion/adapted/struct.hpp>
#include <boost/fusion/include/for_each.hpp>
#include <boost/phoenix/phoenix.hpp>



struct Users
{
	unsigned id;
	std::string mail;
	std::string password;
	unsigned id_staff;
};

BOOST_FUSION_ADAPT_STRUCT(Users, (unsigned,id)(std::string,mail)(std::string,password)(unsigned,id_staff))
endif // Users_HPP
