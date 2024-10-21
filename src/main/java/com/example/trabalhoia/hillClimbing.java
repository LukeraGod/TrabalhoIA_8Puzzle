package com.example.trabalhoia;

import javafx.scene.control.Button;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class hillClimbing {

    int[][] mat;
    int[][] matF;
    int nivel;

    int x;
    int y;

    public hillClimbing(int[][] mat, Button[][] matF, int nivel, int x, int y) {
        this.mat = novaMatriz(mat);
        this.matF = new int[3][3];
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(matF[i][j].getText().compareTo(" ")!=0)
                    this.matF[i][j] = Integer.parseInt(matF[i][j].getText());
                else
                    this.matF[i][j] = -1;
            }
        }
        this.nivel = nivel;
        this.x = x;
        this.y = y;
    }

    private int[][] novaMatriz(int[][] mat) {
        int[][] nMat = new int[3][3];
        for(int i=0;i<3;i++){
            for (int j = 0; j < 3; j++) {
                nMat[i][j] = mat[i][j];
            }
        }
        return nMat;
    }

    public no resolver(){

        try {
            List<no> listaNos = new ArrayList<>();

            // ir para direita
            if (y + 1 < HelloController.matY) {
                int btn = mat[x][y];
                int btnj = mat[x][y + 1];


                List<Integer> listaI = new ArrayList<>();
                listaI.add(0);

                mat[x][y] = btnj;
                mat[x][y + 1] = btn;


                if (!contemVisitado(mat, HelloController.listaVisitados)) {
                    int[][] matAux = new int[3][3];
                    HelloController.copiarMatriz(mat,matAux);
                    listaNos.add(new no(matAux, listaI, null, x, y + 1));
                }

                mat[x][y] = btn;
                mat[x][y + 1] = btnj;
            }

            // ir para esquerda
            if (y - 1 >= 0) {
                int btn = mat[x][y];
                int btnj = mat[x][y - 1];

                List<Integer> listaI = new ArrayList<>();
                listaI.add(1);


                mat[x][y] = btnj;
                mat[x][y - 1] = btn;

                if (!contemVisitado(mat, HelloController.listaVisitados)) {
                    int[][] matAux = new int[3][3];
                    HelloController.copiarMatriz(mat,matAux);
                    listaNos.add(new no(matAux, listaI, null, x, y - 1));
                }

                mat[x][y] = btn;
                mat[x][y - 1] = btnj;
            }

            // baixo
            if (x + 1 < HelloController.matX) {
                int btn = mat[x][y];
                int btnj = mat[x + 1][y];

                List<Integer> listaI = new ArrayList<>();
                listaI.add(2);


                mat[x][y] = btnj;
                mat[x + 1][y] = btn;
                if (!contemVisitado(mat, HelloController.listaVisitados)) {
                    int[][] matAux = new int[3][3];
                    HelloController.copiarMatriz(mat,matAux);
                    listaNos.add(new no(mat, listaI, null, x + 1, y));
                }

                mat[x][y] = btn;
                mat[x + 1][y] = btnj;

            }

            // cima
            if (x - 1 >= 0) {
                int btn = mat[x][y];
                int btnj = mat[x - 1][y];

                List<Integer> listaI = new ArrayList<>();
                listaI.add(3);


                mat[x][y] = btnj;
                mat[x - 1][y] = btn;
                if (!contemVisitado(mat, HelloController.listaVisitados)) {
                    int[][] matAux = new int[3][3];
                    HelloController.copiarMatriz(mat,matAux);
                    listaNos.add(new no(mat, listaI, null, x - 1, y));
                }

                mat[x][y] = btn;
                mat[x - 1][y] = btnj;
            }


            no menorHeuristica = null;
            int menorH;
            if (nivel == 1) {
                if(!listaNos.isEmpty()) {
                    menorHeuristica = listaNos.get(0);
                    menorH = calcularHeuristica(listaNos.get(0));
                }
                else
                    return null;
                for (int i = 1; i < listaNos.size() && menorH != 0; i++) {
                    int heu = calcularHeuristica(listaNos.get(i));
                    if (heu < menorH) {
                        menorHeuristica = listaNos.get(i);
                        menorH = heu;
                    }
                }

                HelloController.listaVisitados.add(menorHeuristica.getMat());

            }
            if (nivel == 2) {

            }

            return menorHeuristica;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    private boolean contemVisitado(int[][] mat, List<int[][]> visitados) {
        try {
            for(int[][] matAux : visitados){
                int cont = 0;
                for(int i=0;i<3;i++){
                    for(int j=0;j<3;j++){
                        if(mat[i][j] == matAux[i][j])
                            cont++;
                    }
                }
                if(cont == 9)
                    return true;
            }

            return false;

        }catch(Exception e){
            System.out.println("ERRO AQUI: "+e.getMessage());
        }
        return false;
    }


    private int calcularHeuristica(no no) {
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

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    private int[] buscarPos(int num) {
        int[] vet = new int[2];
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


}
