import java.util.Random;

public class ClassePrincipal {    
    public static class Item {
        int value;
        int weight;

        public Item(int value, int weight) {
            this.value = value;
            this.weight = weight;
        }
    }

    /**
     * Algoritmo da mochila (0/1) com programação dinâmica.
     * @param items Array de items.
     * @param capacity Capacidade máxima da mochila.
     * @return Valor máximo que pode ser obtido.
     */
    public static int knapsackDP(Item[] items, int capacity) {
        int n = items.length;
        // Cria a tabela de DP
        // dp[i][w] vai armazenar o valor máximo para uma capacidade w usando os primeiros i itens
        int[][] dp = new int[n + 1][capacity + 1];

        // Constrói a tabela dp[][] de baixo para cima
        for (int i = 0; i <= n; i++) {
            for (int w = 0; w <= capacity; w++) {
                if (i == 0 || w == 0) {
                    // Caso base: sem itens ou sem capacidade, o valor é 0
                    dp[i][w] = 0;
                } else if (items[i - 1].weight <= w) {
                    // O item i-1 pode ser incluído.
                    // Decide se inclui o item ou não, pegando o máximo valor entre as duas opções.
                    int valueWithItem = items[i - 1].value + dp[i - 1][w - items[i - 1].weight];
                    int valueWithoutItem = dp[i - 1][w];
                    dp[i][w] = Math.max(valueWithItem, valueWithoutItem);
                } else {
                    // O item i-1 é muito pesado para a capacidade atual w, não pode ser incluído.
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        // O resultado final está na última célula da tabela
        return dp[n][capacity];
    }

    public static void main(String[] args) {
        //Define o número de itens para cada teste
        int[] testSizes = {1_000, 5_000, 10_000, 50_000, 100_000, 250_000};
        int capacity = 2000; // Capacidade fixa para os testes
        Random random = new Random();

        System.out.println("--- Teste de performance do algoritmo de mochila (0/1) com Programação Dinâmica ---");
        System.out.printf("%-15s | %-15s | %-20s%n", "Item Count", "Capacity", "Tempo (ms)");
        System.out.println(new String(new char[60]).replace('\0', '-'));

        //Itera sobre o tamanho dos testes
        for (int size : testSizes) {
            //Gera items randômicamente
            Item[] items = new Item[size];
            for (int i = 0; i < size; i++) {
                //Peso randômico entre 1 e 1001
                int value = random.nextInt(1000) + 1;
                int weight = random.nextInt(1000) + 1;
                items[i] = new Item(value, weight);
            }

            // Mede o tempo de execução do algoritmo de programação dinâmica
            long startTime = System.nanoTime();
            knapsackDP(items, capacity);
            long endTime = System.nanoTime();
            //Converte nanosegundos para milisegundos
            double duration = (endTime - startTime) / 1_000_000.0;

            //Imprime os resultados
            System.out.printf("%,-15d | %,-15d | %,-20.3f%n", size, capacity, duration);
        }
    }
}

