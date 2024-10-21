package com.example.trabalhoia;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    static List<int[][]> listaVisitados = new ArrayList<>();
    static List<noA> listaNos = new ArrayList<>();
    static List<noB> listaNosB = new ArrayList<>();
    static int matY = 3;
    static int matX = 3;
    public AnchorPane telaFundo;

    @FXML
    public TextField DefEstadoFinal;
    @FXML
    public Button btnEstadoFinal;
    @FXML
    public TextField txtTempoGasto;
    @FXML
    public TextField txtNosVisitados;
    @FXML
    public TextField txtDistanciaManhattan;
    public TextArea consoleArea;
    @FXML
    public TextField txtQtdeCaminhos;

    @FXML
    private ComboBox<String> comboNivelBusca;

    @FXML
    private ComboBox<String> comboTipoBusca;

    private List<Integer> listaInstrucoes = new ArrayList<>();

    Button[][] vetB = new Button[matX][matY];
    Button[][] vetBF = new Button[matX][matY];

    int quantNivel=1;
    String tipo="A*";

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        OutputStream outputStream = new OutputStream() {
            @Override
            public void write(int b) {
                consoleArea.appendText(String.valueOf((char) b));
            }
        };

        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);
        System.setErr(printStream);

        iniciarPuzzle();
    }


    public void atualizarInformacoesBusca(double tempoGasto, int nosVisitados, int distanciaManhattan, int tamanhoCaminho) {
        System.out.println("Tempo Gasto: " + tempoGasto + " segundos");
        System.out.println("Nos Visitados: " + nosVisitados);
        System.out.println("Quantidade Caminhos: "+ tamanhoCaminho +" movimentos");

        txtTempoGasto.setText(String.format("%.2f segundos", tempoGasto));
        txtNosVisitados.setText(String.valueOf(nosVisitados));
        txtQtdeCaminhos.setText(String.valueOf(tamanhoCaminho));
    }


    private void iniciarPuzzle() {
        System.out.println("Trabalho IA 8-puzzle inicializando...");
        int y=1;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                vetB[i][j] = (Button) telaFundo.lookup("#btn_"+(y+i+j));

            }
            y+=2;
        }

        y=1;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++) {
                vetBF[i][j] = (Button) telaFundo.lookup("#btnF_" + (y + i + j));

            }
            y+=2;
        }

        if (comboNivelBusca != null) {

            comboNivelBusca.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                String[] newValL = newVal.split(" ");
                quantNivel = Integer.parseInt(newValL[newValL.length-1]);
                System.out.println("Nivel de Busca selecionado: " + newValL[newValL.length-1]);
            });
        }

        if (comboTipoBusca != null) {

            comboTipoBusca.getSelectionModel().selectedItemProperty().addListener((obss, oldVall, tipo) -> {
                this.tipo = tipo;
                System.out.println("Tipo de Busca selecionado: " + this.tipo);
            });
        }
    }



    public Thread embaralhar(){
        Task<Void> task = new Task<Void>(){
            @Override
            protected Void call() {
                int distanciaManhattan = 0;
                long startTime = System.nanoTime();
                int nosVisitados = 0;
                int tamanhoCaminho = 0;
                int x = 0;
                int y = 0;

                while(x<3 && vetB[x][y].getText().compareTo(" ")!=0){
                    while(y<3 && vetB[x][y].getText().compareTo(" ")!=0)
                        y++;
                    if(y==3) {
                        x++;
                        y=0;
                    }
                }


                int ant = -1;

                for(int k=0;k<200;k++){

                    int rand = new Random().nextInt(4);
                    while(((rand == 0 || rand == 1) && (ant == 0 || ant ==1)) || ((rand == 2 || rand == 3) && (ant == 2 || ant ==3)))
                        rand = new Random().nextInt(4);
                    ant = rand;
                    // ir para direita
                    if (rand == 0 && y + 1 < matY) {
                        Button btn = vetB[x][y];
                        Button btnj = vetB[x][y + 1];
                        int diff = (int) Math.abs(btnj.getLayoutX() - btn.getLayoutX());

                        for (int ii = 0; ii < diff; ii++) {
                            btn.setLayoutX(btn.getLayoutX() + 1);
                            btnj.setLayoutX(btnj.getLayoutX() - 1);
                            if(ii%5 == 0) {
                                try {
                                    Thread.sleep(3);
                                } catch (InterruptedException e) {
                                }
                            }
                        }

                        vetB[x][y] = btnj;
                        vetB[x][y + 1] = btn;
                        y++;
                    }

                    // ir para esquerda
                    else if (rand == 1 && y - 1 >= 0) {
                        Button btn = vetB[x][y];
                        Button btnj = vetB[x][y - 1];
                        int diff = (int) Math.abs(btnj.getLayoutX() - btn.getLayoutX());

                        for (int ii = 0; ii < diff; ii++) {
                            btn.setLayoutX(btn.getLayoutX() - 1);
                            btnj.setLayoutX(btnj.getLayoutX() + 1);
                            if(ii%5 == 0) {
                                try {
                                    Thread.sleep(3);
                                } catch (InterruptedException e) {
                                }
                            }
                        }

                        vetB[x][y] = btnj;
                        vetB[x][y - 1] = btn;
                        y--;
                    }

                    // baixo
                    else if (rand == 2 && x + 1 < matX) {
                        Button btn = vetB[x][y];
                        Button btnj = vetB[x + 1][y];
                        int diff = (int) Math.abs(btnj.getLayoutY() - btn.getLayoutY());

                        for (int ii = 0; ii < diff; ii++) {
                            btn.setLayoutY(btn.getLayoutY() + 1);
                            btnj.setLayoutY(btnj.getLayoutY() - 1);
                            if(ii%5 == 0) {
                                try {
                                    Thread.sleep(3);
                                } catch (InterruptedException e) {
                                }
                            }
                        }

                        vetB[x][y] = btnj;
                        vetB[x + 1][y] = btn;
                        x++;
                    }

                    // cima
                    else if (rand == 3 && x - 1 >= 0) {
                        Button btn = vetB[x][y];
                        Button btnj = vetB[x - 1][y];
                        int diff = (int) Math.abs(btnj.getLayoutY() - btn.getLayoutY());

                        for (int ii = 0; ii < diff; ii++) {
                            btn.setLayoutY(btn.getLayoutY() - 1);
                            btnj.setLayoutY(btnj.getLayoutY() + 1);
                            if(ii%5 == 0) {
                                try {
                                    Thread.sleep(3);
                                } catch (InterruptedException e) {
                                }
                            }
                        }

                        vetB[x][y] = btnj;
                        vetB[x - 1][y] = btn;
                        x--;
                    }
                }



                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                }

                x=0;y=0;
                while(vetB[x][y].getText().compareTo(" ")!=0){
                    while(y<3 && vetB[x][y].getText().compareTo(" ")!=0){
                        y++;
                    }
                    if(y==3){
                        y=0;
                        x++;
                    }
                }

                if(tipo.compareTo("A*")==0) {
                    noA obj = null;
                    nosVisitados = 0;
                    int menorH = 0;
                    noA menorHeuristica;
                    while (!(obj != null && menorH - obj.getDist() == 0)) {

                        aEstrela aE;

                        // quando tem anterior
                        if (obj != null)
                        {
                            aE = new aEstrela(obj.getMat(), vetBF, 1, obj.getX(), obj.getY(), obj.getListaI(), obj.getDist(), obj.getVisitados());
                        }

                            // quando nao tem
                        else {
                            int[][] matriz = new int[3][3];
                            converterMat(vetB, matriz);
                            aE = new aEstrela(matriz, vetBF, 1, x, y, null, 0, null);
                        }
                        aE.resolver();

                        if (!listaNos.isEmpty()) {
                            menorHeuristica = listaNos.get(0);
                            menorH = calcularHeuristica(listaNos.get(0)) + listaNos.get(0).dist;
                        } else
                            return null;

                        for (int i = 1; i < listaNos.size() && menorH - menorHeuristica.getDist() != 0; i++) {
                            int heu = calcularHeuristica(listaNos.get(i));
                            if (heu + listaNos.get(i).dist < menorH) {
                                menorHeuristica = listaNos.get(i);
                                menorH = heu + listaNos.get(i).dist;
                            }
                        }
                        obj = menorHeuristica;
                        int i = listaNos.lastIndexOf(obj);
                        listaNos.remove(i);
                        nosVisitados++;

                    }
                    tamanhoCaminho = obj.getListaI().size();
                    int l = 1;
                    for (Integer inte : obj.getListaI()) {
                        if (inte == 0) {
                            Button btn = vetB[x][y];
                            Button btnj = vetB[x][y + 1];
                            int diff = (int) Math.abs(btnj.getLayoutX() - btn.getLayoutX());

                            for (int ii = 0; ii < diff; ii++) {
                                btn.setLayoutX(btn.getLayoutX() + 1);
                                btnj.setLayoutX(btnj.getLayoutX() - 1);
                                if (ii % 5 == 0) {
                                    try {
                                        Thread.sleep(3);
                                    } catch (InterruptedException e) {
                                    }
                                }
                            }

                            vetB[x][y] = btnj;
                            vetB[x][y + 1] = btn;
                            y++;
                        }

                        // ir para esquerda
                        else if (inte == 1) {
                            Button btn = vetB[x][y];
                            Button btnj = vetB[x][y - 1];
                            int diff = (int) Math.abs(btnj.getLayoutX() - btn.getLayoutX());

                            for (int ii = 0; ii < diff; ii++) {
                                btn.setLayoutX(btn.getLayoutX() - 1);
                                btnj.setLayoutX(btnj.getLayoutX() + 1);
                                if (ii % 5 == 0) {
                                    try {
                                        Thread.sleep(3);
                                    } catch (InterruptedException e) {
                                    }
                                }
                            }

                            vetB[x][y] = btnj;
                            vetB[x][y - 1] = btn;
                            y--;
                            nosVisitados++;
                        }

                        // baixo
                        else if (inte == 2) {
                            Button btn = vetB[x][y];
                            Button btnj = vetB[x + 1][y];
                            int diff = (int) Math.abs(btnj.getLayoutY() - btn.getLayoutY());

                            for (int ii = 0; ii < diff; ii++) {
                                btn.setLayoutY(btn.getLayoutY() + 1);
                                btnj.setLayoutY(btnj.getLayoutY() - 1);
                                if (ii % 5 == 0) {
                                    try {
                                        Thread.sleep(3);
                                    } catch (InterruptedException e) {
                                    }
                                }
                            }

                            vetB[x][y] = btnj;
                            vetB[x + 1][y] = btn;
                            x++;
                            nosVisitados++;
                        }

                        // cima
                        else if (inte == 3) {
                            Button btn = vetB[x][y];
                            Button btnj = vetB[x - 1][y];
                            int diff = (int) Math.abs(btnj.getLayoutY() - btn.getLayoutY());

                            for (int ii = 0; ii < diff; ii++) {
                                btn.setLayoutY(btn.getLayoutY() - 1);
                                btnj.setLayoutY(btnj.getLayoutY() + 1);
                                if (ii % 5 == 0) {
                                    try {
                                        Thread.sleep(3);
                                    } catch (InterruptedException e) {
                                    }
                                }
                            }

                            vetB[x][y] = btnj;
                            vetB[x - 1][y] = btn;
                            x--;
                            nosVisitados++;
                        }
                    }
                    distanciaManhattan = menorH;

                }
                // BEST FIRST
                else
                if(tipo.compareTo("Best First") == 0){
                    nosVisitados = 0;
                    noB obj = null;
                    int menorH = 0;
                    noB menorHeuristica;
                    while (!(obj != null && menorH == 0)) {

                        bestFirst aB;

                        // quando tem anterior
                        if (obj != null)
                        {

                            aB = new bestFirst(obj.getMat(), vetBF, 1, obj.getX(), obj.getY(), obj.getListaI(),obj.getVisitados());
                            // quando nao tem
                        }
                        else {
                            int[][] matriz = new int[3][3];
                            converterMat(vetB, matriz);
                            aB = new bestFirst(matriz, vetBF, 1, x, y, null, null);

                        }
                        aB.resolver();

                        if (!listaNosB.isEmpty()) {
                            menorHeuristica = listaNosB.get(0);
                            menorH = calcularHeuristicaB(listaNosB.get(0));
                            distanciaManhattan = menorH;
                        } else
                            return null;

                        for (int i = 1; i < listaNosB.size() && menorH != 0; i++) {
                            int heu = calcularHeuristicaB(listaNosB.get(i));
                            if (heu < menorH) {
                                menorHeuristica = listaNosB.get(i);
                                menorH = heu;
                            }
                        }

                        obj = menorHeuristica;
                        int i = listaNosB.lastIndexOf(obj);
                        listaNosB.remove(i);

                    }

                    tamanhoCaminho = obj.getListaI().size();

                    for (Integer inte : obj.getListaI()) {
                        if (inte == 0) {
                            Button btn = vetB[x][y];
                            Button btnj = vetB[x][y + 1];
                            int diff = (int) Math.abs(btnj.getLayoutX() - btn.getLayoutX());

                            for (int ii = 0; ii < diff; ii++) {
                                btn.setLayoutX(btn.getLayoutX() + 1);
                                btnj.setLayoutX(btnj.getLayoutX() - 1);
                                if (ii % 5 == 0) {
                                    try {
                                        Thread.sleep(3);
                                    } catch (InterruptedException e) {
                                    }
                                }
                            }

                            vetB[x][y] = btnj;
                            vetB[x][y + 1] = btn;
                            y++;
                        }

                        // ir para esquerda
                        else if (inte == 1) {
                            Button btn = vetB[x][y];
                            Button btnj = vetB[x][y - 1];
                            int diff = (int) Math.abs(btnj.getLayoutX() - btn.getLayoutX());

                            for (int ii = 0; ii < diff; ii++) {
                                btn.setLayoutX(btn.getLayoutX() - 1);
                                btnj.setLayoutX(btnj.getLayoutX() + 1);
                                if (ii % 5 == 0) {
                                    try {
                                        Thread.sleep(3);
                                    } catch (InterruptedException e) {
                                    }
                                }
                            }

                            vetB[x][y] = btnj;
                            vetB[x][y - 1] = btn;
                            y--;
                            nosVisitados++;
                        }

                        // baixo
                        else if (inte == 2) {
                            Button btn = vetB[x][y];
                            Button btnj = vetB[x + 1][y];
                            int diff = (int) Math.abs(btnj.getLayoutY() - btn.getLayoutY());

                            for (int ii = 0; ii < diff; ii++) {
                                btn.setLayoutY(btn.getLayoutY() + 1);
                                btnj.setLayoutY(btnj.getLayoutY() - 1);
                                if (ii % 5 == 0) {
                                    try {
                                        Thread.sleep(3);
                                    } catch (InterruptedException e) {
                                    }
                                }
                            }

                            vetB[x][y] = btnj;
                            vetB[x + 1][y] = btn;
                            x++;
                        }

                        // cima
                        else if (inte == 3) {
                            Button btn = vetB[x][y];
                            Button btnj = vetB[x - 1][y];
                            int diff = (int) Math.abs(btnj.getLayoutY() - btn.getLayoutY());

                            for (int ii = 0; ii < diff; ii++) {
                                btn.setLayoutY(btn.getLayoutY() - 1);
                                btnj.setLayoutY(btnj.getLayoutY() + 1);
                                if (ii % 5 == 0) {
                                    try {
                                        Thread.sleep(3);
                                    } catch (InterruptedException e) {
                                    }
                                }
                            }

                            vetB[x][y] = btnj;
                            vetB[x - 1][y] = btn;
                            x--;
                            nosVisitados++;

                        }
                    }
                }
                else{
                    int xEst = x;
                    int yEst = y;

                    no noEle = new no();
                    while(noEle!=null) {

                        System.out.println(x+":"+y);
                        hillClimbing hill;
                        if(noEle.getMat() == null) {
                            int[][] matAux = new int[3][3];
                            converterMat(vetB,matAux);
                            listaVisitados.add(matAux);
                            hill = new hillClimbing(matAux, vetBF, 1, x, y);
                        }
                        else {
                            hill = new hillClimbing(noEle.getMat(), vetBF, 1, x, y);
                        }
                        noEle = hill.resolver();
                        if(noEle!=null){
                            x = noEle.getX();
                            y = noEle.getY();
                            listaInstrucoes.addAll(noEle.getListaI());
                        }
                    }

                    System.out.println("           Estados                ");
                    System.out.println(x+":"+y);
                    System.out.println(xEst+":"+yEst);

                    for(int[][] mat: listaVisitados){
                        for(int i=0;i<3;i++){
                            for(int j=0;j<3;j++){
                                System.out.print(" "+mat[i][j]+" ");
                            }
                        }
                        System.out.println("\n\n");
                    }
                    x = xEst;
                    y = yEst;
                    for(Integer ele:listaInstrucoes){
                        System.out.println(ele);

                        if (ele == 0 && y + 1 < matY) {
                            Button btn = vetB[x][y];
                            Button btnj = vetB[x][y + 1];
                            int diff = (int) Math.abs(btnj.getLayoutX() - btn.getLayoutX());

                            for (int ii = 0; ii < diff; ii++) {
                                btn.setLayoutX(btn.getLayoutX() + 1);
                                btnj.setLayoutX(btnj.getLayoutX() - 1);
                                if(ii%7 == 0) {
                                    try {
                                        Thread.sleep(3);
                                    } catch (InterruptedException e) {
                                    }
                                }
                            }

                            vetB[x][y] = btnj;
                            vetB[x][y + 1] = btn;
                            y++;
                        }

                        // ir para esquerda
                        else if (ele == 1 && y - 1 >= 0) {
                            Button btn = vetB[x][y];
                            Button btnj = vetB[x][y - 1];
                            int diff = (int) Math.abs(btnj.getLayoutX() - btn.getLayoutX());

                            for (int ii = 0; ii < diff; ii++) {
                                btn.setLayoutX(btn.getLayoutX() - 1);
                                btnj.setLayoutX(btnj.getLayoutX() + 1);
                                if(ii%7 == 0) {
                                    try {
                                        Thread.sleep(3);
                                    } catch (InterruptedException e) {
                                    }
                                }
                            }

                            vetB[x][y] = btnj;
                            vetB[x][y - 1] = btn;
                            y--;
                            nosVisitados++;
                        }

                        // baixo
                        else if (ele == 2 && x + 1 < matX) {
                            Button btn = vetB[x][y];
                            Button btnj = vetB[x + 1][y];
                            int diff = (int) Math.abs(btnj.getLayoutY() - btn.getLayoutY());

                            for (int ii = 0; ii < diff; ii++) {
                                btn.setLayoutY(btn.getLayoutY() + 1);
                                btnj.setLayoutY(btnj.getLayoutY() - 1);
                                if(ii%7 == 0) {
                                    try {
                                        Thread.sleep(3);
                                    } catch (InterruptedException e) {
                                    }
                                }
                            }

                            vetB[x][y] = btnj;
                            vetB[x + 1][y] = btn;
                            x++;
                            nosVisitados++;
                        }

                        // cima
                        else if (ele == 3 && x - 1 >= 0) {
                            Button btn = vetB[x][y];
                            Button btnj = vetB[x - 1][y];
                            int diff = (int) Math.abs(btnj.getLayoutY() - btn.getLayoutY());

                            for (int ii = 0; ii < diff; ii++) {
                                btn.setLayoutY(btn.getLayoutY() - 1);
                                btnj.setLayoutY(btnj.getLayoutY() + 1);
                                if(ii%7 == 0) {
                                    try {
                                        Thread.sleep(3);
                                    } catch (InterruptedException e) {
                                    }
                                }
                            }

                            vetB[x][y] = btnj;
                            vetB[x - 1][y] = btn;
                            x--;
                            nosVisitados++;
                        }
//                        exibir(vetB);

                    }
                }

                listaVisitados = new ArrayList<>();
                listaNos = new ArrayList<>();
                listaNosB = new ArrayList<>();

                listaInstrucoes = new ArrayList<>();
                double endTime = System.nanoTime();
                double tempoGasto = (endTime - startTime) / 1_000_000_000.0; // Tempo em segundos

                // Atualizando os campos de texto
                atualizarInformacoesBusca(tempoGasto, nosVisitados, distanciaManhattan, tamanhoCaminho);
                return null;
            }



            private void exibir(Button[][] vetB) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        System.out.print(""+vetB[i][j].getText()+" ");
                    }
                }
                System.out.println("");
            }

            private boolean completo(Button[][] vetB, Button[][] vetBF) {

                int i=0,j;
                while(i<3){
                    j=0;
                    while(j<3 && vetB[i][j].getText().compareTo(vetBF[i][j].getText())==0)
                        j++;
                    i++;
                }

                return i == 4;
            }

        };
        Thread thread = new Thread(task);
        thread.start();
        return thread;

    }


    private int calcularHeuristica(noA no) {
        int[][] mat = no.getMat();
        int valorT = 0;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                int[] pos = buscarPos(mat[i][j]);
                valorT+= (Math.abs(i-pos[0])+Math.abs(j-pos[1]));
            }
        }

        return valorT;
    }
    private int calcularHeuristicaB(noB no) {
        int[][] mat = no.getMat();
        int valorT = 0;
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                int[] pos = buscarPos(mat[i][j]);
                valorT+= (Math.abs(i-pos[0])+Math.abs(j-pos[1]));
            }
        }
        return valorT;

    }
    private int[] buscarPos(int num) {
        int[] vet = new int[2];
        int[][] matF = new int[3][3];
        converterMat(vetBF,matF);
        int i=0,j=0;
        while(i<3 && matF[i][j]!=num){
            while(j<3 && matF[i][j]!=num){
                j++;
            }
            if(j>=3){
                i++;
                j=0;
            }
        }
        vet[0] = i;
        vet[1] = j;
        return vet;
    }

    private void converterMat(Button[][] vetBF, int[][] matF) {
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(vetBF[i][j].getText().compareTo(" ")!=0)
                    matF[i][j] = Integer.parseInt(vetBF[i][j].getText());
                else
                    matF[i][j] = -1;
            }
        }
    }

   public static void copiarMatriz(int[][] vetBF, int[][] matF) {
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                matF[i][j] = vetBF[i][j];
            }
        }
    }



    public void resolverHillClimbing(){
        int x=0,y=0;
        while(vetB[x][y].getText().compareTo(" ")!=0){
            y=0;
            while(y<matY && vetB[x][y].getText().compareTo(" ")!=0){
                y++;
            }
            x++;
        }

}


    public void EstadoFinalSel(ActionEvent actionEvent) {
        int k = 0;
        String txt = DefEstadoFinal.getText();
        if(txt.length()==9 && verificarConteudo(txt)) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    vetB[i][j].setText("" + txt.charAt(k));
                    vetBF[i][j].setText("" + txt.charAt(k++));
                }
            }
        }
        DefEstadoFinal.setDisable(true);
        btnEstadoFinal.setDisable(true);
    }

    public static boolean verificarConteudo(String texto) {
        boolean temEspaco = texto.contains(" ");

        boolean temNumeros = true;
        for (char c = '1'; c <= '8'; c++) {
            if (!texto.contains(String.valueOf(c))) {
                temNumeros = false;
                break;
            }
        }

        return temEspaco && temNumeros;
    }
}