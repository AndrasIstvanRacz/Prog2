#include <stdio.h>
#include <math.h>
#include <stdlib.h>

void kiir (double tomb[], int db) //A kiir függvény felel a kiíratásért.Az int db adja meg, hogy hányszor fut le a fügvényben lévő ciklus.
{
	int i;
	for (i=0; i<db; i++)
		printf("PageRank [%d]: %lf\n", i, tomb[i]);
}

double tavolsag(double pagerank[],double pagerank_temp[],int db)
{
	double tav = 0.0;
	int i;
	for(i=0;i<db;i++)
		if((pagerank[i] - pagerank_temp[i])<0)
		{
			tav +=(-1*(pagerank[i] - pagerank_temp[i]));
		}
		else
		{
			tav +=(pagerank[i] - pagerank_temp[i]);
		}
	return tav;
}

int main(void)
{
	double L[4][4] = {			// L= a link mátrix
	{0.0, 0.0, 1.0 / 3.0, 0.0},
	{1.0, 1.0 / 2.0, 1.0 / 3.0, 1.0},
	{0.0, 1.0 / 2.0, 0.0, 0.0},
	{0.0, 0.0, 1.0 / 3.0, 0.0}
	};

	double PR[4] = {0.0, 0.0, 0.0, 0.0}; //Ebben lesz benne a végeredmény
	double PRv[4] = {1.0 / 4.0, 1.0 / 4.0, 1.0 / 4.0, 1.0 / 4.0}; // Az oldatak presztízs

	long int i,j;
	i=0; j=0;

	for (;;)
	{
		for(i=0;i<4;i++)
			PR[i] = PRv[i]; //Átadja PRv-t a PR-nek
		for (i=0;i<4;i++)
		{
			double temp=0;
			for (j=0;j<4;j++)
				temp+=L[i][j]*PR[j]; //Az L mátrixot összeszoroza a PR vektorral.
			PRv[i]=temp;
		}	

		if ( tavolsag(PR,PRv, 4) < 0.00001) //A tavlság függvényt meghívva határoza meg, hogy mikor áll le a ciklus.
		break;
	}
	kiir (PR,4);	//Meghívjuk a kiir függvényt a kiiratáshoz
	return 0;
}
