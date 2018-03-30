#ifndef PRODUCTSTATE_HPP
#define PRODUCTSTATE_HPP

#include <string>
#include <boost/fusion/adapted/struct.hpp>
#include <boost/fusion/include/for_each.hpp>
#include <boost/phoenix/phoenix.hpp>


struct ProductState
{
	unsigned productStateId;
	unsigned quantity;
	unsigned version;
	unsigned productId;
	unsigned storeId;
};

BOOST_FUSION_ADAPT_STRUCT(ProductState, (unsigned,productStateId)(unsigned,quantity)(unsigned,version)(unsigned,productId)(unsigned,storeId))

#endif // PRODUCTSTATE_HPP