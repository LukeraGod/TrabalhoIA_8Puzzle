package com.example.trabalhoia;

import javafx.scene.control.Button;

import java.util.List;

public class noA {
    int[][] mat;
    List<Integer> listaI;

    int x,y;
    int dist;

    List<int[][]> visitados;

    public int getDist() {
        return dist;
    }

    public void setDist(int dist) {
        this.dist = dist;
    }

    public noA(int[][] mat, List<Integer> listaI, int x, int y, int dist,List<int[][]> Visitados) {
        this.mat = new int[3][3];
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                this.mat[i][j] = mat[i][j];
            }
        }
        this.listaI = listaI;
        this.x = x;
        this.y = y;
        this.dist = dist;
        this.visitados = Visitados;
    }


    public int[][] getMat() {
        return mat;
    }

    public void setMat(int[][] mat) {
        this.mat = mat;
    }

    public List<Integer> getListaI() {
        return listaI;
    }

    public void setListaI(List<Integer> listaI) {
        this.listaI = listaI;
    }

    public List<int[][]> getVisitados() {
        return visitados;
    }

    public void setVisitados(List<int[][]> visitados) {
        this.visitados = visitados;
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

}
