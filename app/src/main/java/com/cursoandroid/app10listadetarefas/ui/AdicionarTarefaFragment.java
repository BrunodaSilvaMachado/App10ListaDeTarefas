package com.cursoandroid.app10listadetarefas.ui;

import android.os.Bundle;
import android.view.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.cursoandroid.app10listadetarefas.R;
import com.cursoandroid.app10listadetarefas.databinding.FragmentAdicionarTarefaBinding;
import com.cursoandroid.app10listadetarefas.helper.TarefaDAO;
import com.cursoandroid.app10listadetarefas.model.Tarefa;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class AdicionarTarefaFragment extends Fragment {

    private FragmentAdicionarTarefaBinding binding;
    private TextInputEditText editText;
    private Tarefa tarefaAtual;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentAdicionarTarefaBinding.inflate(inflater, container, false);
        editText = binding.textTarefa;
        tarefaAtual = (getArguments() == null)? null:
             (Tarefa) getArguments().getSerializable("tarefaSelecionada");
        if (tarefaAtual != null){
            editText.setText(tarefaAtual.getNomeTarefa());
        }
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_adicionar_tarefa,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.itemSalvar) {
            String nomeTarefa = Objects.requireNonNull(editText.getText()).toString();
            TarefaDAO tarefaDAO = new TarefaDAO(getContext());
            Tarefa tarefa = new Tarefa();
            tarefa.setNomeTarefa(nomeTarefa);
            boolean sucess;

            if (!nomeTarefa.isEmpty()) {
                if (tarefaAtual != null) {//editar
                    tarefa.setId(tarefaAtual.getId());
                    sucess = tarefaDAO.atualizar(tarefa);
                }
                else {//salvar
                    sucess = tarefaDAO.salvar(tarefa);
                }
                if (sucess){
                    NavHostFragment.findNavController(AdicionarTarefaFragment.this)
                            .navigate(R.id.action_SecondFragment_to_FirstFragment);
                }else{
                    Snackbar.make(Objects.requireNonNull(getView()), "Não é possivel realizar a operação",Snackbar.LENGTH_LONG).show();
                }

            }else {
                Snackbar.make(Objects.requireNonNull(getView()), "Digite um texto primeiro",Snackbar.LENGTH_LONG).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}