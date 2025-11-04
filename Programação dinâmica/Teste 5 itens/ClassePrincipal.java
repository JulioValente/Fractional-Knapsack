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
        // Definindo um conjunto fixo de 5 itens
        Item[] items = {
            new Item(60, 10),
            new Item(100, 20),
            new Item(120, 30),
            new Item(80, 40),
            new Item(50, 50)
        };

        // Capacidade da mochila
        int capacity = 50;

        System.out.println("--- Teste Simples da Mochila Fracionária ---");
        System.out.println("Capacidade da mochila: " + capacity);
        System.out.println("Itens disponíveis (valor, peso): (60,10), (100,20), (120,30), (80,40), (50,50)");    

        // Mede o tempo de execução do algoritmo de programação dinâmica            
        int maxValue = knapsackDP(items, capacity);            
        
        System.out.printf("%nO valor máximo que pode ser obtido é: %d%n", maxValue);
        
    }
}

