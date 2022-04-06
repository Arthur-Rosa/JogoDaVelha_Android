package br.senai.sp.cotia.jogodavelha.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import br.senai.sp.cotia.jogodavelha.R;
import br.senai.sp.cotia.jogodavelha.databinding.FragmentJogoBinding;
import br.senai.sp.cotia.jogodavelha.util.PrefsUtil;

public class JogoFragment extends Fragment {
    // variavel para acessar os elementos da View
    private FragmentJogoBinding binding;
    // vetor de botões para referenciar os botões
    private Button[] botoes;
    // matriz de string do tabuuleiro
    private String[][] tabuleiro;
    // variaveis para as bolinha
    private String simbJog1, simbJog2, simbolo;
    // variavel para indicar qual jogador inicia a partida
    private Random random;
    // variavel para controlar numero de jogadas
    private int numJogadas = 0;
    // variaveis para o placar
    private int placarJog1 = 0, placarJog2 = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // habilitar o menu
        setHasOptionsMenu(true);

        // instanciando o binding
        binding = FragmentJogoBinding.inflate(inflater, container, false);

        // instanciar o vetor
        botoes = new Button[9];

        // instanciar o tabuleiro
        tabuleiro = new String[3][3];

        // preenche matriz com string vazia

        for (String[] vetor : tabuleiro) {
            Arrays.fill(vetor, "");
        }

        // define o simbolos dos jogadores 1 e 2
        simbJog1 = PrefsUtil.getSimboloJog1(getContext());
        simbJog2 = PrefsUtil.getSimboloJog2(getContext());

        binding.text1.setText(getResources().getString(R.string.jogador_1,simbJog1));
        binding.text2.setText(getResources().getString(R.string.jogador_2,simbJog2));

        // instanciar o random
        random = new Random();

        // sorteara quem começa o jogo
        sorteia();

        // atualiza a vez
        atualizaVez();

        // associar o vetor aos botões

        botoes[0] = binding.bt00;
        botoes[1] = binding.bt01;
        botoes[2] = binding.bt02;
        botoes[3] = binding.bt10;
        botoes[4] = binding.bt11;
        botoes[5] = binding.bt12;
        botoes[6] = binding.bt20;
        botoes[7] = binding.bt21;
        botoes[8] = binding.bt22;

        // associa o listener aos botões
        for (Button bt : botoes) {
            bt.setOnClickListener(listenerBotoes);
        }

        // retorna root do binding
        return binding.getRoot();
    }

    private void atualizaPlacar(){
        binding.placarUm.setText(placarJog1+"");
        binding.placarDois.setText(placarJog2+"");
    }

    private void sorteia() {
        // se gerar um valor verdadeiro jogador 1 começa
        // caso contrario jogador 2 começa
        if (random.nextBoolean()) {
            simbolo = simbJog1;
        } else {
            simbolo = simbJog2;
        }
    }

    private void reseta() {
        for (Button bt : botoes) {
            bt.setClickable(true);
            bt.setBackgroundColor(getResources().getColor(R.color.black));
            bt.setText("");
        }

        for (String[] vetor : tabuleiro) {
            Arrays.fill(vetor, "");
        }

        numJogadas = 0;

        sorteia();
        atualizaVez();
    }

    private void atualizaVez() {

        if (simbolo.equals(simbJog1)) {
            // simbolo = simbJog2;

            binding.linearLayout.setBackgroundColor(getResources().getColor(R.color.gray));
            binding.linearLayout2.setBackgroundColor(getResources().getColor(R.color.black));

            binding.text1.setTextColor(getResources().getColor(R.color.black));
            binding.text2.setTextColor(getResources().getColor(R.color.white));
            binding.placarUm.setTextColor(getResources().getColor(R.color.black));
            binding.placarDois.setTextColor(getResources().getColor(R.color.white));
        } else {
            // simbolo = simbJog1;

            binding.text1.setTextColor(getResources().getColor(R.color.white));
            binding.text2.setTextColor(getResources().getColor(R.color.black));
            binding.placarUm.setTextColor(getResources().getColor(R.color.white));
            binding.placarDois.setTextColor(getResources().getColor(R.color.black));

            binding.linearLayout.setBackgroundColor(getResources().getColor(R.color.black));
            binding.linearLayout2.setBackgroundColor(getResources().getColor(R.color.gray));
        }
    }

    private boolean venceu() {
        // verifica se venceu nas linhas
        for (int li = 0; li < 3; li++) {
            if (tabuleiro[li][0].equals(simbolo) && tabuleiro[li][1].equals(simbolo) && tabuleiro[li][2].equals(simbolo)) {
                return true;
            }
        }
        // verifica se venceu nas colunas
        for (int col = 0; col < 3; col++) {
            if (tabuleiro[0][col].equals(simbolo) && tabuleiro[1][col].equals(simbolo) && tabuleiro[2][col].equals(simbolo)) {
                return true;
            }
        }

        // verifica nas diagonais
        if (tabuleiro[0][0].equals(simbolo) && tabuleiro[1][1].equals(simbolo) && tabuleiro[2][2].equals(simbolo)) {
            return true;
        }
        if (tabuleiro[0][2].equals(simbolo) && tabuleiro[1][1].equals(simbolo) && tabuleiro[2][0].equals(simbolo)) {
            return true;
        }
        return false;
    }
/*
    public void vezDoRobo() {
        Log.w("entrou", "na vez do robo");
        Random r = new Random();
        numJogadas += 1;
        simbolo = "O";
        boolean z = false;

        while (z != true) {
            int linha = r.nextInt(3);
            int coluna = r.nextInt(3);

            if (tabuleiro[linha][coluna].equals(simbolo)) {
                Log.w("robo ja tem", "ja tem ja tem ja tem");
                z = false;
            } else {



                tabuleiro[linha][coluna] = simbolo;

                botoes[0] = binding.bt00;
                botoes[1] = binding.bt01;
                botoes[2] = binding.bt02;
                botoes[3] = binding.bt10;
                botoes[4] = binding.bt11;
                botoes[5] = binding.bt12;
                botoes[6] = binding.bt20;
                botoes[7] = binding.bt21;
                botoes[8] = binding.bt22;

                if (tabuleiro[linha][coluna] == tabuleiro[0][0] && botoes[0].getText().equals("")) {
                    Button botao = botoes[0];
                    botao.setText(simbolo);
                    botao.setTextColor(Color.BLACK);
                    botao.setBackgroundColor(Color.RED);
                    botao.setClickable(false);

                    z = true;
                } else if (tabuleiro[linha][coluna] == tabuleiro[0][1] && botoes[1].getText().equals("")) {
                    Button botao = botoes[1];
                    botao.setText(simbolo);
                    botao.setTextColor(Color.BLACK);
                    botao.setBackgroundColor(Color.RED);
                    botao.setClickable(false);

                    z = true;
                } else if (tabuleiro[linha][coluna] == tabuleiro[0][2] && botoes[2].getText().equals("")) {
                    Button botao = botoes[2];
                    botao.setText(simbolo);
                    botao.setTextColor(Color.BLACK);
                    botao.setBackgroundColor(Color.RED);
                    botao.setClickable(false);

                    z = true;
                } else if (tabuleiro[linha][coluna] == tabuleiro[1][0] && botoes[3].getText().equals("")) {
                    Button botao = botoes[3];
                    botao.setText(simbolo);
                    botao.setTextColor(Color.BLACK);
                    botao.setBackgroundColor(Color.RED);
                    botao.setClickable(false);

                    z = true;
                } else if (tabuleiro[linha][coluna] == tabuleiro[1][1] && botoes[4].getText().equals("")) {
                    Button botao = botoes[4];
                    botao.setText(simbolo);
                    botao.setTextColor(Color.BLACK);
                    botao.setBackgroundColor(Color.RED);
                    botao.setClickable(false);

                    z = true;
                } else if (tabuleiro[linha][coluna] == tabuleiro[1][2] && botoes[5].getText().equals("")) {
                    Button botao = botoes[5];
                    botao.setText(simbolo);
                    botao.setTextColor(Color.BLACK);
                    botao.setBackgroundColor(Color.RED);
                    botao.setClickable(false);

                    z = true;
                } else if (tabuleiro[linha][coluna] == tabuleiro[2][0] && botoes[6].getText().equals("")) {
                    Button botao = botoes[6];
                    botao.setText(simbolo);
                    botao.setTextColor(Color.BLACK);
                    botao.setBackgroundColor(Color.RED);
                    botao.setClickable(false);

                    z = true;
                } else if (tabuleiro[linha][coluna] == tabuleiro[2][1] && botoes[7].getText().equals("")) {
                    Button botao = botoes[7];
                    botao.setText(simbolo);
                    botao.setTextColor(Color.BLACK);
                    botao.setBackgroundColor(Color.RED);
                    botao.setClickable(false);

                    z = true;
                } else if (tabuleiro[linha][coluna] == tabuleiro[2][2] && botoes[8].getText().equals("")) {
                    Button botao = botoes[8];
                    botao.setText(simbolo);
                    botao.setTextColor(Color.BLACK);
                    botao.setBackgroundColor(Color.RED);
                    botao.setClickable(false);

                    z = true;
                }
            }
        }
    }
*/
    private View.OnClickListener listenerBotoes = btPress -> {
        // incrementa numero de jogadas
        numJogadas++;

        // obtem o nome do botao
        String nomeBotao = getContext().getResources().getResourceName(btPress.getId());
        // extrai a posição
        String posicao = nomeBotao.substring(nomeBotao.length() - 2);
        // extrai linha e coluna da String posição
        int linha = Character.getNumericValue(posicao.charAt(0));
        int coluna = Character.getNumericValue(posicao.charAt(1));
        // preencher a posição da matriz com o simbolo da vez
        tabuleiro[linha][coluna] = simbolo;
        // faz um casting de view para button
        Button botao = (Button) btPress;
        // "seta" o simbolo do botao pressionado
        botao.setText(simbolo);
        // troca o background para branco
        botao.setBackgroundColor(Color.WHITE);
        // troca cor da letra para preto
        botao.setTextColor(Color.BLACK);
        // desabilita botao que foi jogado
        botao.setClickable(false);

        if (numJogadas >= 0 && venceu()) {

            // informa que houve um vencedor
            Toast.makeText(getContext(), R.string.venceu, Toast.LENGTH_SHORT).show();

            if(simbolo.equals(simbJog1)){
                placarJog1++;
            } else {
                placarJog2++;
            }
            // atualiza placar
            atualizaPlacar();
            // reseta
            reseta();
        } else if (numJogadas == 9) {
            // informa que deu velha
            Toast.makeText(getContext(), R.string.deuvelha, Toast.LENGTH_LONG).show();
            // reseta
            reseta();
        } else {
            simbolo = simbolo.equals(simbJog1) ? simbJog2 : simbJog1;
            atualizaVez();
        }
    };

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // verificar qual opção foi selecionada
        switch (item.getItemId()){
            // caso seja o opção de resetar
            case R.id.menu_resetar:
                placarJog2 = 0;
                placarJog1 = 0;
                atualizaPlacar();
                reseta();
                break;
            // caso seja a opção de preferencias
            case R.id.menu_pref:
                NavHostFragment.findNavController(JogoFragment.this).navigate(R.id.action_jogoFragment_to_prefFragment);
                break;
        }

        return true;
    }
}