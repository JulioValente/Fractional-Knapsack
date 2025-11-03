import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class ClassePrincipal {    
    public static class Item {
        int value;
        int weight;
        Double cost; //Razão valor/peso

        public Item(int value, int weight) {
            this.value = value;
            this.weight = weight;
            //Calcula a razão (custo)
            this.cost = (double) value / (double) weight;
        }
    }

    /**
     * Algorítmo da mochila fracionária.
     * @param items Array de items.
     * @param capacity Capacidade máxima da mochila.
     * @return Valor que máximo que pode ser obtido.
     */
    public static double getMaxValue(Item[] items, int capacity) {        
        //Ordena os items por valor/peso (custo) em ordem decrescente
        Arrays.sort(items, new Comparator<Item>(){
            @Override
            public int compare(Item item1, Item item2) {
                return item2.cost.compareTo(item1.cost);
            }
        });

        double totalValue = 0.0;
        int currentCapacity = capacity;

        //Itera sobre os items ordenados
        for (Item item : items) {
            //Para se a mochila já está cheia
            if (currentCapacity == 0){
                break;
            }

            //Se o item inteiro pode ser utilizado
            if(item.weight <= currentCapacity){            
                //Adiciona o valor do item e reduz a capacidade da mochila
                totalValue += item.value;
                currentCapacity -= item.weight;
            }else{ //Se somente uma fração do item pode ser utilizada                
                //Calcula a fração que cabe na mochila
                double fraction = (double) currentCapacity / (double) item.weight;
                
                //Adiciona o valor da fração do item
                totalValue += item.value * fraction;
                
                //A partir de agora a mochila está cheia
                currentCapacity = 0;
            }
        }

        return totalValue;
    }

    public static void main(String[] args) {
        //Define o número de itens para cada teste
        int[] testSizes = {1_000, 5_000, 10_000, 50_000, 100_000, 250_000, 500_000};
        Random random = new Random();

        System.out.println("--- Teste de performance do algoritmo de mochila fracionária ---");
        System.out.printf("%-15s | %-25s | %-25s%n", "Item Count", "Tempo items desordenados (ms)", "Tempo itens ordenados (ms)");
        System.out.println(new String(new char[75]).replace('\0', '-'));

        //Itera sobre o tamanho dos testes
        for (int size : testSizes) {
            //Gera items randômicamente
            Item[] items = new Item[size];
            long totalWeight = 0;
            for (int i = 0; i < size; i++) {
                //Peso randômico entre 1 e 1001
                int value = random.nextInt(1000) + 1;
                int weight = random.nextInt(1000) + 1;
                items[i] = new Item(value, weight);
                totalWeight += weight;
            }
            //Seta a capacidade para 50% sobre a soma de todos os itens
            int capacity = (int) (totalWeight / 2);

            //Teste 1: Entrada desordenada

            Item[] itemsForUnsortedTest = Arrays.copyOf(items, items.length);
            
            long startTimeUnsorted = System.nanoTime();
            getMaxValue(itemsForUnsortedTest, capacity);
            long endTimeUnsorted = System.nanoTime();
            //Converte nanosegundos para milisegundos
            double durationUnsorted = (endTimeUnsorted - startTimeUnsorted) / 1_000_000.0;

            //Teste 2: Entrada ordenada
            
            //Ordenando a array
            Item[] itemsForSortedTest = Arrays.copyOf(items, items.length);            
            Arrays.sort(itemsForSortedTest, Comparator.comparingDouble((Item item) -> item.cost).reversed());

            long startTimeSorted = System.nanoTime();
            getMaxValue(itemsForSortedTest, capacity);
            long endTimeSorted = System.nanoTime();
            //Converte nanosegundos para milisegundos
            double durationSorted = (endTimeSorted - startTimeSorted) / 1_000_000.0;

            //Imprime os resultados
            System.out.printf("%,-15d | %-25.3f | %-25.3f%n", size, durationUnsorted, durationSorted);
        }
    }
}
