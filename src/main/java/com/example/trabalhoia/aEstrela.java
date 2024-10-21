package com.example.trabalhoia;

import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;

import static com.example.trabalhoia.HelloController.listaNos;

public class aEstrela {
    int[][] mat;
    int[][] matF;
    int nivel;
    int dist;
    int x;
    int y;
    List<Integer> listaIns;
    List<int[][]> visitados;

    public aEstrela(int[][] mat, Button[][] matF, int nivel, int x, int y,List<Integer> listaI,int dist,List<int[][]> visitados) {
        this.mat = mat;
        this.matF = new int[3][3];
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(matF[i][j].getText().compareTo(" ")!=0)
                    this.matF[i][j] = Integer.parseInt(matF[i][j].getText());
                else
                    this.matF[i][j] = -1;
            }
        }
        listaIns = listaI;
        this.nivel = nivel;
        this.x = x;
        this.y = y;
        this.dist = dist;

        if(visitados == null){
            visitados = new ArrayList<>();
        }
        this.visitados = visitados;
    }
    public void resolver(){



            visitados.add(mat);


            // ir para direita
            if (y + 1 < HelloController.matY) {
                int btn = mat[x][y];
                int btnj = mat[x][y + 1];


                List<Integer> listaI = new ArrayList<>();
                for(int i=0;listaIns!=null && i<listaIns.size();i++){
                    Integer ele = listaIns.get(i);
                    listaI.add(ele);
                }
                listaI.add(0);

                int[][] matAux = new int[3][3];
                HelloController.copiarMatriz(mat,matAux);
                matAux[x][y] = btnj;
                matAux[x][y + 1] = btn;

                if(!contem(matAux,visitados))
                    listaNos.add(new noA(matAux, listaI, x, y + 1,dist+1,visitados));

            }

            // ir para esquerda
            if (y - 1 >= 0) {
                int btn = mat[x][y];
                int btnj = mat[x][y - 1];

                List<Integer> listaI = new ArrayList<>();
                for(int i=0;listaIns!=null && i<listaIns.size();i++){
                    Integer ele = listaIns.get(i);
                    listaI.add(ele);
                }
                listaI.add(1);


                int[][] matAux = new int[3][3];
                HelloController.copiarMatriz(mat,matAux);
                matAux[x][y] = btnj;
                matAux[x][y - 1] = btn;

                if(!contem(matAux,visitados))
                     listaNos.add(new noA(matAux, listaI, x, y - 1,dist+1,visitados));

            }

            // baixo
            if (x + 1 < HelloController.matX) {
                int btn = mat[x][y];
                int btnj = mat[x + 1][y];

                List<Integer> listaI = new ArrayList<>();
                for(int i=0;listaIns!=null && i<listaIns.size();i++){
                    Integer ele = listaIns.get(i);
                    listaI.add(ele);
                }
                listaI.add(2);

                int[][] matAux = new int[3][3];
                HelloController.copiarMatriz(mat,matAux);
                matAux[x][y] = btnj;
                matAux[x + 1][y] = btn;

                if(!contem(matAux,visitados))
                    listaNos.add(new noA(matAux, listaI, x + 1, y,dist+1,visitados));

            }

            // cima
            if (x - 1 >= 0) {
                int btn = mat[x][y];
                int btnj = mat[x - 1][y];

                List<Integer> listaI = new ArrayList<>();
                for(int i=0;listaIns!=null && i<listaIns.size();i++){
                    Integer ele = listaIns.get(i);
                    listaI.add(ele);
                }
                listaI.add(3);


                int[][] matAux = new int[3][3];
                HelloController.copiarMatriz(mat,matAux);
                matAux[x][y] = btnj;
                matAux[x - 1][y] = btn;

                if(!contem(matAux,visitados))
                    listaNos.add(new noA(matAux, listaI, x - 1, y,dist+1,visitados));

            }
        }

    private boolean contem(int[][] mat, List<int[][]> visitados) {
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
    }

}


