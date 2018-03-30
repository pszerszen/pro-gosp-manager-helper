#include "../data/Supply.hpp"
#include "../data/Store.hpp"
#include "../data/Product.hpp"
#include "../data/Transaction.hpp"
#include "../data/productState.hpp"

class ProductStateGenerator
{
public:
	ProductStateGenerator():idCnt(0){}

	productState generateByProductForStore(unsigned storeId, unsigned productId,
	 const Supply* supp, unsigned sizeOfSupply,
	 const Transaction* trns, unsigned sizeOfTransaction)
	{
		productState productState;
		productState.productStateId = idCnt;
		idCnt++;

		for(unsigned i = 0; i < sizeOfSupply; ++i)
		{
			if(supp.productId == productId)
			{
				productState.quantity += supp.quantity;
			}
		}

		for(unsigned i = 0; i < sizeOfSupply; ++i)
		{
			if(supp.productId == productId)
			{
				productState.quantity += supp.quantity;
			}
		}

		productState.version = 0;
		productState.productId = productId;
		productState.storeId = storeId
	}

	ProductState generate(const Supply* supp, unsigned sizeOfSupply,
		const Transaction* trns, unsigned sizeOfTransaction)
	{
		for(unsigned i = 0; i < sizeOfSupply; ++i)
		{
			outputTable[supp[i].receiverId][supp[i].productId];
		}

		for(unsigned i = 0; i < sizeOfSupply; ++i)
		{
			if(supp.productId == productId)
			{
				productState.quantity += supp.quantity;
			}
		}
	}

	void setStoreTablePtr(Store* stores)
	{
		storeTablePtr = stores;
	}

	void setProductTablePtr(Product* products)
	{
		productTablePtr = products;
	}

private:
	unsigned idCnt;
	Store storeTablePtr;
	Product productTablePtr;

	ProductState outputTable[20][100];
}