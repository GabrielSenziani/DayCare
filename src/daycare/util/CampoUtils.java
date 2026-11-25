package daycare.util;

import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.JTextComponent;

public final class CampoUtils {

    private CampoUtils() {
    }

    public static void limitarCaracteres(JTextComponent campo, int limiteMaximo) {
        limitarCaracteres(campo, limiteMaximo, false);
    }

    public static void limitarCaracteres(JTextComponent campo, int limiteMaximo, boolean apenasLetras) {
        if (campo == null || limiteMaximo <= 0) {
            return;
        }
        AbstractDocument doc = (AbstractDocument) campo.getDocument();
        doc.setDocumentFilter(new LimiteDocumentFilter(limiteMaximo, apenasLetras));
    }

    public static void formatarCPF(JTextComponent campo) {
        if (campo == null) {
            return;
        }
        AbstractDocument doc = (AbstractDocument) campo.getDocument();
        doc.setDocumentFilter(new CpfDocumentFilter());
    }

    public static String formatarCPFTexto(String cpf) {
        if (cpf == null || cpf.isEmpty()) {
            return cpf;
        }
        String digits = cpf.replaceAll("\\D", "");
        if (digits.length() <= 3) {
            return digits;
        } else if (digits.length() <= 6) {
            return digits.substring(0, 3) + "." + digits.substring(3);
        } else if (digits.length() <= 9) {
            return digits.substring(0, 3) + "." + digits.substring(3, 6) + "." + digits.substring(6);
        } else {
            return digits.substring(0, 3) + "." + digits.substring(3, 6) + "." + digits.substring(6, 9) + "-" + digits.substring(9, Math.min(11, digits.length()));
        }
    }

    private static final class LimiteDocumentFilter extends DocumentFilter {

        private final int limiteMaximo;
        private final boolean apenasLetras;

        LimiteDocumentFilter(int limiteMaximo, boolean apenasLetras) {
            this.limiteMaximo = limiteMaximo;
            this.apenasLetras = apenasLetras;
        }

        private String filtrarTexto(String texto) {
            if (!apenasLetras || texto == null) {
                return texto;
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < texto.length(); i++) {
                char c = texto.charAt(i);
                if (Character.isLetter(c) || c == ' ' || c == '-' || c == '\'') {
                    sb.append(c);
                }
            }
            return sb.toString();
        }

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (string == null) {
                return;
            }
            String textoFiltrado = filtrarTexto(string);
            if (textoFiltrado.isEmpty()) {
                return;
            }
            int tamanhoAtual = fb.getDocument().getLength();
            int tamanhoInsercao = textoFiltrado.length();
            int tamanhoTotal = tamanhoAtual + tamanhoInsercao;
            if (tamanhoTotal <= limiteMaximo) {
                super.insertString(fb, offset, textoFiltrado, attr);
            } else {
                int caracteresDisponiveis = limiteMaximo - tamanhoAtual;
                if (caracteresDisponiveis > 0) {
                    String trecho = textoFiltrado.substring(0, caracteresDisponiveis);
                    super.insertString(fb, offset, trecho, attr);
                }
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (text == null) {
                text = "";
            }
            String textoFiltrado = filtrarTexto(text);
            int tamanhoAtual = fb.getDocument().getLength();
            int tamanhoRemocao = length;
            int tamanhoInsercao = textoFiltrado.length();
            int tamanhoTotal = tamanhoAtual - tamanhoRemocao + tamanhoInsercao;
            if (tamanhoTotal <= limiteMaximo) {
                super.replace(fb, offset, length, textoFiltrado, attrs);
            } else {
                int caracteresDisponiveis = limiteMaximo - (tamanhoAtual - tamanhoRemocao);
                if (caracteresDisponiveis > 0) {
                    String trecho = textoFiltrado.substring(0, Math.min(tamanhoInsercao, caracteresDisponiveis));
                    super.replace(fb, offset, length, trecho, attrs);
                } else {
                    super.replace(fb, offset, length, "", attrs);
                }
            }
        }
    }

    private static final class CpfDocumentFilter extends DocumentFilter {

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (string == null) {
                return;
            }
            String digits = extrairDigitos(fb.getDocument().getText(0, fb.getDocument().getLength()) + string);
            String formatado = formatarCPF(digits);
            substituirConteudo(fb, 0, fb.getDocument().getLength(), formatado);
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (text == null) {
                text = "";
            }
            String textoAtual = fb.getDocument().getText(0, fb.getDocument().getLength());
            String novoTexto = textoAtual.substring(0, offset) + text + textoAtual.substring(offset + length);
            String digits = extrairDigitos(novoTexto);
            String formatado = formatarCPF(digits);
            substituirConteudo(fb, 0, fb.getDocument().getLength(), formatado);
        }

        private String extrairDigitos(String texto) {
            return texto.replaceAll("\\D", "");
        }

        private String formatarCPF(String digits) {
            if (digits.length() <= 3) {
                return digits;
            } else if (digits.length() <= 6) {
                return digits.substring(0, 3) + "." + digits.substring(3);
            } else if (digits.length() <= 9) {
                return digits.substring(0, 3) + "." + digits.substring(3, 6) + "." + digits.substring(6);
            } else {
                return digits.substring(0, 3) + "." + digits.substring(3, 6) + "." + digits.substring(6, 9) + "-" + digits.substring(9, Math.min(11, digits.length()));
            }
        }

        private void substituirConteudo(FilterBypass fb, int offset, int length, String texto) throws BadLocationException {
            super.replace(fb, offset, length, texto, null);
        }
    }
}
