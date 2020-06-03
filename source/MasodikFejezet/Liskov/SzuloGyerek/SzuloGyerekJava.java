class szulo
{
    public void szulo_vagyok()
    {
        System.out.println("Én vagyok én.");
    }
};


class gyerek extends szulo
{
    public void gyerek_vagyok(String x)
    {
        System.out.println(x);
    }

    public static void main(String[] args)
    {
        szulo sz = new szulo();
        szulo sz2 = new gyerek();
        System.out.println("A szülő meghívó módszere");
        sz.szulo_vagyok();
        System.out.println("A gyermek meghívása a szülőn referencián  keresztül ");
        sz2.gyerek_vagyok("Nem működik");
        
    }
};
