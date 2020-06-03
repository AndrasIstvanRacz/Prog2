#include <stdio.h>	//printf-et tartalmaza
#include <stdlib.h>	//Ez tartalmaza az abs függvényt
#include <unistd.h> 	//Ez az usleep-hez szükséges

//Az ablak méretnek megadása
#define SZEL 78 //forditáskor SZEL 78-al lesz egyenlő
#define MAG 22	//forditáskor MAG 22-vel lesz egyenlő

int putX(int x,int y) 		//putX függvény	
{
	int i;

	for(i=0;i<x;i++)	// a függőleges koordinátát határozza meg
		printf("\n");

	for(i=0;i<y;i++)	// a vízszintes koordinátát határozza meg
		printf(" ");

	printf("o\n"); 		// a Labda

	return 0;
}

int main()
{
	int x=0,y=0;

	while(1)
	{
		system("clear"); // Ez törli az elöző labdát az új kiírása előt
		putX(abs(MAG-(x++%(MAG*2))),abs(SZEL-(y++%(SZEL*2)))); // ez határoza meg a labda pozicióját amihez a putX függvényt hivja meg
		usleep(50000); // a labda sebességét határoza meg a program altatásával
	}

	return 0;
}
