#include <iostream>

class szulo
{
    public:
        void szulo_vagyok()
        {
            std::cout << "Én vagyok én." << std::endl;
        }
};


class gyerek : public szulo
{
    public:
        void gyerek_vagyok(std::string x)
        {
            std::cout << x << std::endl;
        }
};


int main ( int argc, char **argv )
{
    szulo* p= new szulo();
    szulo* p2= new gyerek();
    std::cout << 'A szülő meghívó módszere';
    p->szulo_vagyok();
    std::cout << "A gyermek meghívása a szülőn keresztül ref\n";
    p2->gyerek_vagyok("Nem működik");
}