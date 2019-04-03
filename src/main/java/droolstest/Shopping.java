package droolstest;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

public class Shopping {
    public static final void main(String[] args) {
        KieContainer kc = KieServices.Factory.get().getKieClasspathContainer();
        System.out.println(kc.verify().getMessages().toString());
        execute( kc );
    }

    public static void execute( KieContainer kc ) {
        KieSession ksession = kc.newKieSession("ShoppingKS");

        Customer vito = new Customer( "Vitalya",
                0 );
        ksession.insert( vito );

        Product cigarettes = new Product( "cigarettes",
                60 );
        ksession.insert( cigarettes );

        Product beer = new Product( "beer",
                60 );
        ksession.insert( beer );

        ksession.insert( new Purchase( vito,
                cigarettes ) );

        FactHandle beerPurchaseHandle = ksession.insert( new Purchase( vito,
                beer ) );

        ksession.fireAllRules();

        ksession.delete( beerPurchaseHandle );
        System.out.println( "Customer mark has returned the hat" );
        ksession.fireAllRules();
    }

    public static class Customer {
        private String name;

        private int    discount;

        public Customer(String name,
                        int discount) {
            this.name = name;
            this.discount = discount;
        }

        public String getName() {
            return name;
        }

        public int getDiscount() {
            return discount;
        }

        public void setDiscount(int discount) {
            this.discount = discount;
        }

    }

    public static class Discount {
        private Customer customer;
        private int      amount;

        public Discount(Customer customer,
                        int amount) {
            this.customer = customer;
            this.amount = amount;
        }

        public Customer getCustomer() {
            return customer;
        }

        public int getAmount() {
            return amount;
        }

    }

    public static class Product {
        private String name;
        private float  price;

        public Product(String name,
                       float price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public float getPrice() {
            return price;
        }

    }

    public static class Purchase {
        private Customer customer;
        private Product  product;

        public Purchase(Customer customer,
                        Product product) {
            this.customer = customer;
            this.product = product;
        }

        public Customer getCustomer() {
            return customer;
        }

        public Product getProduct() {
            return product;
        }
    }
}
