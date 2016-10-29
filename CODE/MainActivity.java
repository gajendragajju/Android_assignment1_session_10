public class MainActivity extends Activity implements OnFragmentTouched {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addFragment(View v) {
        int randomColor =
                Color.argb(255, (int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
        Fragment fragment = CircularRevealingFragment.newInstance(20, 20, randomColor);
        getFragmentManager().beginTransaction().add(R.id.container, fragment).commit();
    }

    @Override
    public void onFragmentTouched(Fragment fragment, float x, float y) {
        if (fragment instanceof CircularRevealingFragment) {
            final CircularRevealingFragment theFragment = (CircularRevealingFragment) fragment;

            Animator unreveal = theFragment.prepareUnrevealAnimator(x, y);

            unreveal.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    // remove the fragment only when the animation finishes
                    getFragmentManager().beginTransaction().remove(theFragment).commit();
                    //to prevent flashing the fragment before removing it, execute pending transactions inmediately
                    getFragmentManager().executePendingTransactions();
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
            unreveal.start();
        }
    }